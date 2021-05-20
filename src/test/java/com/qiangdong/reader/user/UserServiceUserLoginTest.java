package com.qiangdong.reader.user;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.enums.common.LoginPlatformEnum;
import com.qiangdong.reader.interceptor.JwtInterceptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class UserServiceUserLoginTest extends BaseTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void userLoginSuccessful() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
            MockMvcRequestBuilders.post("/user/login")
                .header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP.description())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"mobile\": \"13715166407\", \"password\": \"123456\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse();
        String expireToken = response.getHeader(JwtInterceptor.HEADER_AUTHENTICATION);
        assertThat(expireToken).isNotEmpty();
    }

    @Test
    public void userLoginTestWhenMobileNoExist() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/user/login")
                .header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"mobile\": \"13715166403\", \"password\": \"123456\"}"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
