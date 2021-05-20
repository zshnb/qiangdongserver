package com.qiangdong.reader.user;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user.UserDto;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.common.LoginPlatformEnum;
import com.qiangdong.reader.interceptor.JwtInterceptor;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import com.qiangdong.reader.utils.JwtUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class UserServiceTokenLoginTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void tokenLoginSuccessful() throws Exception {
        String token = jwtUtil.signJwtToken(userId, LoginPlatformEnum.APP.description());
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/user/token-login")
                        .header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP.description())
                        .header(JwtInterceptor.HEADER_AUTHENTICATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();
    }

    @Test
    public void tokenLoginFailedWhenUserNoExist() throws Exception {
        String token = jwtUtil.signJwtToken(-1L, LoginPlatformEnum.APP.description());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/token-login")
                        .header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP.description())
                        .header(JwtInterceptor.HEADER_AUTHENTICATION, token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
