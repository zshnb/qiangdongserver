package com.qiangdong.reader.user;

import static org.mockito.ArgumentMatchers.any;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.common.UserConstant;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.common.LoginPlatformEnum;
import com.qiangdong.reader.interceptor.JwtInterceptor;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import com.qiangdong.reader.utils.UserUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.TimeUnit;

public class UserServiceFastLoginTest extends BaseTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    @SpyBean
    private UserUtil userUtil;

    @Before
    public void beforeMock() {
        Mockito.doNothing().when(userUtil).indexUser(any());
    }

    @Test
    public void FastLoginSuccessfulWhenUserNoExist() throws Exception {
        String key = String.format(UserConstant.KEY_USER_FAST_LOGIN_VERIFY_CODE, "13715166707");
        redisTemplate.opsForValue().set(key, "123456",
            UserConstant.KEY_USER_FAST_LOGIN_VERIFY_CODE_TIMEOUT, TimeUnit.SECONDS);
        mockMvc.perform(
            MockMvcRequestBuilders.post("/user/fast-login")
                .header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.WEB.description())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"mobile\": \"13715166707\", \"code\": \"123456\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk());

        User user = userService.getOne(new QueryWrapper<User>()
            .eq("mobile", "13715166707"));
        assertThat(user.getId()).isGreaterThan(0L);
    }

    @Test
    public void secondFastLoginSuccessfulWhenUserExist() throws Exception {
        String key = String.format(UserConstant.KEY_USER_FAST_LOGIN_VERIFY_CODE, "13715166404");
        redisTemplate.opsForValue().set(key, "123456",
                UserConstant.KEY_USER_FAST_LOGIN_VERIFY_CODE_TIMEOUT, TimeUnit.SECONDS);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/fast-login")
                        .header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.WEB.description())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mobile\": \"13715166404\", \"code\": \"123456\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void fastLoginWhenVerifyCodeNoExist() throws Exception {
        String key = String.format(UserConstant.KEY_USER_FAST_LOGIN_VERIFY_CODE, "13715166404");
        redisTemplate.opsForValue().set(key, "123457",
                UserConstant.KEY_USER_FAST_LOGIN_VERIFY_CODE_TIMEOUT, TimeUnit.SECONDS);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/fast-login")
                        .header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.WEB.description())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mobile\": \"13715166404\", \"code\": \"123456\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
