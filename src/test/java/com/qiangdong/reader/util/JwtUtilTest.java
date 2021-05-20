package com.qiangdong.reader.util;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.enums.common.LoginPlatformEnum;
import com.qiangdong.reader.interceptor.JwtInterceptor;
import com.qiangdong.reader.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class JwtUtilTest extends BaseTest {
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void signAndParseSuccessful() {
		String token = jwtUtil.signJwtToken(1L, LoginPlatformEnum.APP.description());
		Claims claims = jwtUtil.parseJwtToken(token);
		Long userId = claims.get(JwtUtil.CLAIM_USER_ID, Long.class);
		Assert.assertEquals(1L, userId.longValue());
	}

	@Test
	public void authenticationSuccessful() throws Exception {
		String token = jwtUtil.signJwtToken(1L, LoginPlatformEnum.APP.description());
		mockMvc.perform(
			MockMvcRequestBuilders.post("/novel/detail/1")
				.header(JwtInterceptor.HEADER_AUTHENTICATION, token)
				.header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP.description())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 1}"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void authenticationFailedWhenTimeout() throws Exception {
		String token = jwtUtil.signJwtToken(1L, LoginPlatformEnum.APP.description());
		mockMvc.perform(
			MockMvcRequestBuilders.post("/novel/detail/1")
				.header(JwtInterceptor.HEADER_AUTHENTICATION, token)
				.header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP.description())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 1}"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		Thread.sleep(3000);
		mockMvc.perform(
			MockMvcRequestBuilders.post("/novel/detail/1")
				.header(JwtInterceptor.HEADER_AUTHENTICATION, token)
				.header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP.description())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 1}"))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	@Test
	public void authenticationFailedWhenNoJwtToken() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/novel/detail/1")
			.header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType()))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	public void authenticationFailedWhenJwtTokenInvalid() throws Exception {
		String token = jwtUtil.signJwtToken(1L, LoginPlatformEnum.APP.description());
		mockMvc.perform(
			MockMvcRequestBuilders.post("/novel/detail/1")
				.header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
				.header(JwtInterceptor.HEADER_AUTHENTICATION,  token.substring(0, token.length() - 1))
				.header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP.description()))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
	}

	@Test
	public void loginThenLogout() throws Exception {
		String token = jwtUtil.signJwtToken(1L, LoginPlatformEnum.APP.description());
		mockMvc.perform(
			MockMvcRequestBuilders.post("/novel/detail/1")
				.header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
				.header(JwtInterceptor.HEADER_AUTHENTICATION, token)
				.header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP.description())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 1}"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		MockHttpServletResponse response = mockMvc.perform(
			MockMvcRequestBuilders.post("/user/logout")
				.header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
				.header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP.description())
				.header(JwtInterceptor.HEADER_AUTHENTICATION, token))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn()
			.getResponse();
		String expireToken = response.getHeader(JwtInterceptor.HEADER_AUTHENTICATION);
		mockMvc.perform(
			MockMvcRequestBuilders.post("/novel/detail/1")
				.header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
				.header(JwtInterceptor.HEADER_AUTHENTICATION, expireToken)
				.header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP.description())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 1}"))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
}
