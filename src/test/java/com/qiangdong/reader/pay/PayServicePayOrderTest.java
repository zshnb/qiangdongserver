package com.qiangdong.reader.pay;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.UserCreditRecordDto;
import com.qiangdong.reader.enums.common.LoginPlatformEnum;
import com.qiangdong.reader.interceptor.JwtInterceptor;
import com.qiangdong.reader.request.user_credit_record.ListUserCreditRecordRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.UserCreditRecordServiceImpl;
import com.qiangdong.reader.utils.JwtUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


public class PayServicePayOrderTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserCreditRecordServiceImpl userCreditRecordService;

    @Test
    public void payOrderSuccessful() throws Exception {
        String token = jwtUtil.signJwtToken(userId, LoginPlatformEnum.APP.description());
        mockMvc.perform(
            MockMvcRequestBuilders.post("/pay/order")
                .header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP)
                .header(JwtInterceptor.HEADER_AUTHENTICATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"transactionWay\": 2, \"price\": 10, \"device\": 1}"))
            .andExpect(MockMvcResultMatchers.status().isOk());

        ListUserCreditRecordRequest request = new ListUserCreditRecordRequest();
        request.setUserId(adminUserId);
        PageResponse<UserCreditRecordDto> response = userCreditRecordService.listUserCreditRecord(request);
        assertThat(response.getList().size()).isEqualTo(4);
        assertThat(response.getList().get(3).getUserId()).isEqualTo(3L);
        assertThat(response.getList().get(3).getPrice().intValueExact()).isEqualTo(20);
    }

    @Test
    public void payOrderFailedWhenWrongDevice() throws Exception {
        String token = jwtUtil.signJwtToken(userId, LoginPlatformEnum.APP.description());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/pay/order")
                        .header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP)
                        .header(JwtInterceptor.HEADER_AUTHENTICATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"transactionWay\": 1, \"price\": 10, \"device\": 0}"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void payOrderFailedWhenWrongTransactionWay() throws Exception {
        String token = jwtUtil.signJwtToken(userId, LoginPlatformEnum.APP.description());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/pay/order")
                        .header(JwtInterceptor.HEADER_LOGIN_PLATFORM, LoginPlatformEnum.APP)
                        .header(JwtInterceptor.HEADER_AUTHENTICATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"transactionWay\": 0, \"price\": 10, \"device\": 1}"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
