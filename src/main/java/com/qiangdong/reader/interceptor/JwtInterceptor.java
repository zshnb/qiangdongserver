package com.qiangdong.reader.interceptor;

import cn.hutool.http.ContentType;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.config.JwtConfig;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.filter.RequestUserIdInjectWrapper;
import com.qiangdong.reader.utils.JwtUtil;
import com.qiangdong.reader.utils.UserUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);
    public static final String HEADER_AUTHENTICATION = "Authentication";
    public static final String HEADER_LOGIN_PLATFORM = "LoginPlatform";
    public static final String KEY_JWT_TOKEN = "jwt-token:%s";
    private static final ThreadLocal<Long> timestampThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> postBodyThreadLocal = new ThreadLocal<>();

    private final JwtUtil jwtUtil;
    private final UserUtil userUtil;
    private final JwtConfig jwtConfig;
    private final UserMapper userMapper;
    private final RedisTemplate<String, String> redisTemplate;

    public JwtInterceptor(JwtUtil jwtUtil, UserUtil userUtil, JwtConfig jwtConfig,
                          UserMapper userMapper,
                          RedisTemplate<String, String> redisTemplate) {
        this.jwtUtil = jwtUtil;
        this.userUtil = userUtil;
        this.jwtConfig = jwtConfig;
        this.userMapper = userMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 不处理springboot框架出错时的http请求
        if (request.getRequestURI().equals("/error")) {
            return true;
        }
        long currentMillis = System.currentTimeMillis();
        timestampThreadLocal.set(currentMillis);
        String token = request.getHeader(HEADER_AUTHENTICATION);
        String headerLoginPlatform = request.getHeader(HEADER_LOGIN_PLATFORM);

        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(headerLoginPlatform)) {
            throw new PermissionDenyException();
        }

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        try {
            Claims claims = jwtUtil.parseJwtToken(token);
            Long userId = claims.get(JwtUtil.CLAIM_USER_ID, Long.class);
            Long signMillSeconds = claims.get(JwtUtil.CLAIM_SIGN_MILL_SECONDS, Long.class);
            if (Instant.now().toEpochMilli() - signMillSeconds > jwtConfig.getExpire()) {
                String redisToken = valueOperations.get(String.format(KEY_JWT_TOKEN, userId));
                if (redisToken == null) {
                    throw new PermissionDenyException();
                }
                redisTemplate.expire(String.format(KEY_JWT_TOKEN, userId),
                    jwtConfig.getExpire() * 2, TimeUnit.MILLISECONDS);
                String newToken = jwtUtil.signJwtToken(userId, headerLoginPlatform);
                response.setHeader(HEADER_AUTHENTICATION, newToken);
            }

            User user = userMapper.selectOne(new QueryWrapper<User>()
                .select("id, last_login_time")
                .eq("id", userId));
            if (user == null) {
                throw new InvalidArgumentException("用户不存在");
            }

            // Cause form data's Content-Type don't have any json request body, so these request
            // Should be passed
            if (!StringUtils.isEmpty(request.getContentType()) &&
                request.getContentType().startsWith(ContentType.MULTIPART.toString())) {
                return true;
            }

            RequestUserIdInjectWrapper wrapper = (RequestUserIdInjectWrapper) request;
            String body = wrapper.getBody();
            JSONObject jsonObject;
            if (StringUtils.isEmpty(body)) {
                jsonObject = new JSONObject();
            } else {
                jsonObject = new JSONObject(body);
            }
            jsonObject.put(JwtUtil.CLAIM_USER_ID, userId);

            // 把pathVariable的值设置到request里相同名称的变量上
            Map<String, String> pathVariableMap = (Map<String, String>) request.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (pathVariableMap != null) {
                pathVariableMap.forEach((k, v) -> {
                    try {
                        jsonObject.put(k, Long.valueOf(v));
                    } catch (NumberFormatException e) {
                        logger.error(String.format("url: %s format path variable: %s value: %s error",
                            request.getRequestURI(), k, v));
                    }
                });
            }
            postBodyThreadLocal.set(jsonObject.toString());
            wrapper.setBody(jsonObject.toString());
            valueOperations.set(String.format(KEY_JWT_TOKEN, userId),
                token, jwtConfig.getExpire() * 2, TimeUnit.MILLISECONDS);
            response.setHeader(HEADER_AUTHENTICATION, token);
            // 跨域的情况下，能够拿到token
            response.setHeader("Access-Control-Expose-Headers", HEADER_AUTHENTICATION);

            userUtil.afterLogin(user);
        } catch (SignatureException e) {
            logger.error("Jwt token authentication error", e);
            throw new PermissionDenyException();
        } catch (ExpiredJwtException e) {
            logger.error("Jwt token expired");
            throw new PermissionDenyException();
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        super.afterCompletion(request, response, handler, ex);
        long startMillis = timestampThreadLocal.get();
        long currentMillis = System.currentTimeMillis();
        long cost = currentMillis - startMillis;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (ex == null) {
                logger.info(String.format("url: %s, method:%s, request: %s, cost: %sms", request.getRequestURI(),
                    handlerMethod.getMethod().getName(), postBodyThreadLocal.get(), cost));
            } else {
                logger.error(String.format("url: %s, method:%s, request: %s error", request.getRequestURI(),
                    handlerMethod.getMethod().getName(), postBodyThreadLocal.get()), ex);
            }
        }
        timestampThreadLocal.remove();
        postBodyThreadLocal.remove();
    }
}
