package com.qiangdong.reader.interceptor;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.enums.common.LoginPlatformEnum;
import com.qiangdong.reader.request.novel.ListAuthorNovelRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import com.qiangdong.reader.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class RequestInjectPathVariableTest extends BaseTest {
	@Autowired
	private NovelServiceImpl novelService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void autoInjectPathVariableSuccessful() throws Exception {
		String token = jwtUtil.signJwtToken(1L, LoginPlatformEnum.APP.description());
		Claims claims = jwtUtil.parseJwtToken(token);
		Long userId = claims.get(JwtUtil.CLAIM_USER_ID, Long.class);
		Assert.assertEquals(1L, userId.longValue());
		mockMvc.perform(
			MockMvcRequestBuilders.delete("/manage/novel/1")
				.header(JwtInterceptor.HEADER_AUTHENTICATION, token)
				.header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP.description())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"novelId\": 1}"))
			.andExpect(MockMvcResultMatchers.status().isOk());

		ListAuthorNovelRequest request = new ListAuthorNovelRequest();
		request.setUserId(1L);
		PageResponse<NovelDto> response = novelService.listAuthorNovel(request);
		Assert.assertEquals(1, response.getList().size());
	}
}
