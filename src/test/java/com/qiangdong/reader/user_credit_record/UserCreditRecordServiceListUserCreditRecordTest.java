package com.qiangdong.reader.user_credit_record;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.UserCreditRecordDto;
import com.qiangdong.reader.request.user_credit_record.ListUserCreditRecordRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.UserCreditRecordServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class UserCreditRecordServiceListUserCreditRecordTest extends BaseTest {

    @Autowired
    private UserCreditRecordServiceImpl userCreditRecordService;

    @Test
    public void listUserCreditRecordSuccessful() {
        ListUserCreditRecordRequest request = new ListUserCreditRecordRequest();
        request.setUserId(5L);
        PageResponse<UserCreditRecordDto> response = userCreditRecordService.listUserCreditRecord(request);
        assertThat(response.getList().size()).isEqualTo(4);
        assertThat(response.getList().get(0).getUsername()).isEqualTo("user1");
        assertThat(response.getList().get(0).getPrice().doubleValue()).isEqualTo(10.00);
    }
}
