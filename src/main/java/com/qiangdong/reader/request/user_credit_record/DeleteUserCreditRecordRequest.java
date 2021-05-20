package com.qiangdong.reader.request.user_credit_record;

import com.qiangdong.reader.request.BaseRequest;

public class DeleteUserCreditRecordRequest extends BaseRequest {
    private Long userCreditRecordId = 0L;

    public Long getUserCreditRecordId() {
        return userCreditRecordId;
    }

    public void setUserCreditRecordId(Long userCreditRecordId) {
        this.userCreditRecordId = userCreditRecordId;
    }
}
