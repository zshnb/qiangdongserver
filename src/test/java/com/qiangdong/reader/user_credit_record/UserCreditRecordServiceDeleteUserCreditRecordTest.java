package com.qiangdong.reader.user_credit_record;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.UserCreditRecordDto;
import com.qiangdong.reader.entity.UserCreditRecord;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_credit_record.DeleteUserCreditRecordRequest;
import com.qiangdong.reader.request.user_credit_record.ListUserCreditRecordRequest;
import com.qiangdong.reader.serviceImpl.UserCreditRecordServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserCreditRecordServiceDeleteUserCreditRecordTest extends BaseTest {

    @Autowired
    private UserCreditRecordServiceImpl userCreditRecordService;

    @Test
    public void deleteUserCreditRecordSuccessful() {
        DeleteUserCreditRecordRequest request = new DeleteUserCreditRecordRequest();
        request.setUserId(5L);
        request.setUserCreditRecordId(1L);
        userCreditRecordService.deleteUserCreditRecord(request, new UserCreditRecord());
        ListUserCreditRecordRequest listUserCreditRecordRequest = new ListUserCreditRecordRequest();
        listUserCreditRecordRequest.setUserId(5L);
        List<UserCreditRecordDto> data = userCreditRecordService.listUserCreditRecord(listUserCreditRecordRequest).getList();
        assertThat(data.size()).isEqualTo(3);
    }

    @Test
    public void deleteUserCreditRecordWhenUserCreditRecordNoExist() {
        DeleteUserCreditRecordRequest request = new DeleteUserCreditRecordRequest();
        request.setUserId(5L);
        request.setUserCreditRecordId(-1L);
        assertException(InvalidArgumentException.class, () -> {
            userCreditRecordService.deleteUserCreditRecord(request, new UserCreditRecord());
        });
    }
}
