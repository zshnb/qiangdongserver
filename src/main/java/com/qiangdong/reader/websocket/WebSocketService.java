package com.qiangdong.reader.websocket;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.qiangdong.reader.common.UserConstant;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.dto.WebSocketMessageDto;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.user.UserChatStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * websocket服务
 */
@Component
@ServerEndpoint(value = "/chatServer")
public class WebSocketService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketService.class);
    private static ConcurrentMap<Long, WebSocketService> websockets = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    private Long userId;      //用户Id
    /**
     * 用户名和websocket的session绑定的路由表
     */
    private static ConcurrentMap<Long, Session> sessionRouter = new ConcurrentHashMap<>();
    private static RedisTemplate<String, String> redisTemplate;
    private static UserMapper userMapper;

    @Autowired
    public void WebSocketService(RedisTemplate<String, String> redisTemplate, UserMapper userMapper) {
        WebSocketService.redisTemplate = redisTemplate;
        WebSocketService.userMapper = userMapper;
    }

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session) {
        this.parameterHandler(session);
        this.addSocket();
        this.session = session;
        sessionRouter.put(userId, session); //绑定用户session
        redisTemplate.opsForZSet().add(UserConstant.ONLINE_USERS, String.valueOf(userId), System.currentTimeMillis());
        userMapper.update(new User(),new UpdateWrapper<User>()
            .set("chat_status", UserChatStatusEnum.ONLINE.code())
            .eq("id", userId));
    }

    private void addSocket() {
        websockets.put(userId, this);
    }

    private void removeSocket() {
        WebSocketService webSocket = websockets.get(userId);
        if (webSocket == null) {
            return;
        }
        websockets.remove(userId);
        websockets.put(userId, webSocket);
    }

    private void parameterHandler(Session session) {
        Map<String, List<String>> parameter = session.getRequestParameterMap();
        if (parameter == null) return;
        List<String> userIds = parameter.get("user_id");
        if (CollectionUtils.isNotEmpty(userIds)) {
            userId = Long.parseLong(userIds.get(0));
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        this.removeSocket();
        sessionRouter.remove(userId);
        redisTemplate.opsForZSet().remove(UserConstant.ONLINE_USERS, String.valueOf(userId));
        userMapper.update(new User(),new UpdateWrapper<User>()
            .set("chat_status", UserChatStatusEnum.OFFLINE.code())
            .eq("id", userId));
    }

    public void sendMsg(WebSocketMessageDto message) {
        WebSocketService chatServer = websockets.get(Long.valueOf(message.getChatUserId()));
        if (chatServer == null) {
            return;
        }
        String msg = JSONUtil.toJsonPrettyStr(message);
        try {
            chatServer.session.getBasicRemote().sendText(msg);
        } catch (Exception e) {
            LOGGER.error("WebSocket发送信息异常", e);
        }
    }

    /**
     * 发生错误时调用
     *
     * @param error
     */
    @OnError
    public void onError(Throwable error) {
        LOGGER.info("websocket error.", error);
    }
}
