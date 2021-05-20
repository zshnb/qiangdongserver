package com.qiangdong.reader.user;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.enums.common.LoginPlatformEnum;
import com.qiangdong.reader.enums.user.UserCreatorIdentityEnum;
import com.qiangdong.reader.interceptor.JwtInterceptor;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class UserServiceManagerLoginTest extends BaseTest {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void authorLoginSuccessful() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
            MockMvcRequestBuilders.post("/manage/user/login")
                .header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP.description())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"account\": \"13715166405\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse();

        String json = response.getContentAsString();
        JSONObject userDto = JSONUtil.parseObj(json);
        assertThat(userDto.getJSONObject("data").getInt("creatorIdentity"))
            .isEqualTo(UserCreatorIdentityEnum.AUTHOR_AND_PAINTER.code());
    }
}
