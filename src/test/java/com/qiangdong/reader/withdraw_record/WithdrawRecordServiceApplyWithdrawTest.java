package com.qiangdong.reader.withdraw_record;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.withdraw_record.ApplyWithdrawRequest;
import com.qiangdong.reader.serviceImpl.WithdrawRecordServiceImpl;
import java.math.BigDecimal;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WithdrawRecordServiceApplyWithdrawTest extends BaseTest {
    @Autowired
    private WithdrawRecordServiceImpl withdrawRecordService;

    @Test
    public void applyWithdrawRecordSuccessful() {
        ApplyWithdrawRequest request = new ApplyWithdrawRequest();
        request.setUserId(authorUserId);
        request.setMoney(new BigDecimal("16.00"));
        String remainMoney = withdrawRecordService.applyWithdraw(request).getData();
        assertThat(remainMoney).isEqualTo("100.00");
    }

    @Test
    public void applyWithdrawRecordFailedWhenOutOfIncome() {
        ApplyWithdrawRequest request = new ApplyWithdrawRequest();
        request.setUserId(authorUserId);
        request.setMoney(new BigDecimal("200.00"));
        assertException(InvalidArgumentException.class, () -> withdrawRecordService.applyWithdraw(request));
    }

    @Test
    public void applyWithdrawRecordFailedWhenPermissionDeny() {
        ApplyWithdrawRequest request = new ApplyWithdrawRequest();
        request.setUserId(userId);
        request.setMoney(new BigDecimal("200.00"));
        assertException(PermissionDenyException.class, () -> withdrawRecordService.applyWithdraw(request));
    }
}
