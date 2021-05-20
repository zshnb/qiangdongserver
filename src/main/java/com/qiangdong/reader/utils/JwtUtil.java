package com.qiangdong.reader.utils;

import cn.hutool.core.date.DateUtil;
import com.qiangdong.reader.config.JwtConfig;
import com.qiangdong.reader.enums.common.LoginPlatformEnum;
import com.qiangdong.reader.interceptor.JwtInterceptor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
	public static String CLAIM_USER_ID = "userId";
	public static String CLAIM_SIGN_MILL_SECONDS = "signMillSeconds";
	public static String CLAIM_LOGIN_PLATFORM = "loginPlatform";

	@Autowired
	private JwtConfig jwtConfig;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 签发token
	 * */
	public String signJwtToken(Long userId, String loginPlatform) {
		Date now = DateUtil.date();
		LoginPlatformEnum loginPlatformEnum = LoginPlatformEnum.parseOf(loginPlatform);
		JwtBuilder builder = Jwts.builder()
			.claim(CLAIM_USER_ID, userId)
			.claim(CLAIM_SIGN_MILL_SECONDS, Instant.now().toEpochMilli())
			.claim(CLAIM_LOGIN_PLATFORM, loginPlatformEnum.description())
			.setSubject(jwtConfig.getSubject())
			.setAudience(jwtConfig.getAudience())
			.setIssuer(jwtConfig.getIssuer())
			.setNotBefore(now)
			.signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey());
		return builder.compact();
	}

	public Claims parseJwtToken(String jwtToken) {
		return Jwts.parser()
			.setSigningKey(jwtConfig.getSecretKey())
			.parseClaimsJws(jwtToken)
			.getBody();
	}

	public String expireJwtToken(String jwtToken) {
		Claims claims = Jwts.parser()
			.setSigningKey(jwtConfig.getSecretKey())
			.parseClaimsJws(jwtToken)
			.getBody();

		long userId = claims.get(CLAIM_USER_ID, Long.class);
		LoginPlatformEnum loginPlatform =
			LoginPlatformEnum.parseOf(claims.get(CLAIM_LOGIN_PLATFORM, String.class));
		JwtBuilder builder = Jwts.builder()
				.claim(CLAIM_USER_ID, userId)
				.claim(CLAIM_SIGN_MILL_SECONDS,
					Instant.now().minusMillis(jwtConfig.getExpire()).toEpochMilli())
				.claim(CLAIM_LOGIN_PLATFORM, loginPlatform.description())
				.setSubject(jwtConfig.getSubject())
				.setAudience(jwtConfig.getAudience())
				.setIssuer(jwtConfig.getIssuer())
				.signWith(SignatureAlgorithm.HS256, jwtConfig.getSecretKey());
		redisTemplate.delete(String.format(JwtInterceptor.KEY_JWT_TOKEN, userId));
		return builder.compact();
	}
}
