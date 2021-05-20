package com.qiangdong.reader.withdraw_record;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.WithdrawRecordDto;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.withdraw_record.ListWithdrawRecordRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.WithdrawRecordServiceImpl;
import java.math.BigDecimal;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WithdrawRecordServiceListWithdrawRecordTest extends BaseTest {
    @Autowired
    private WithdrawRecordServiceImpl withdrawRecordService;

    @Test
    public void listByAuthorSuccessful() {
        ListWithdrawRecordRequest request = new ListWithdrawRecordRequest();
        request.setUserId(authorUserId);
        PageResponse<WithdrawRecordDto> response = withdrawRecordService.listWithdrawRecords(request);
        assertThat(response.getList().size()).isEqualTo(1);
        assertThat(response.getList().get(0).getMoney()).isEqualTo(new BigDecimal("1.00"));
    }

    @Test
    public void listByEditorSuccessful() {
        ListWithdrawRecordRequest request = new ListWithdrawRecordRequest();
        request.setUserId(editorUserId);
        request.setTargetUserId(3L);
        PageResponse<WithdrawRecordDto> response = withdrawRecordService.listWithdrawRecords(request);
        assertThat(response.getList().size()).isEqualTo(1);
        assertThat(response.getList().get(0).getMoney()).isEqualTo(new BigDecimal("2.00"));
        assertThat(response.getList().get(0).getUserId()).isEqualTo(3L);
    }

    @Test
    public void listFailedWhenPermissionDeny() {
        ListWithdrawRecordRequest request = new ListWithdrawRecordRequest();
        request.setUserId(userId);
        assertException(PermissionDenyException.class, () -> withdrawRecordService.listWithdrawRecords(request));
    }
}
