package com.qiangdong.reader.request.withdraw_record;

import com.qiangdong.reader.request.BaseRequest;

public class ListWithdrawRecordRequest extends BaseRequest {
    private Long targetUserId = 0L;

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }
}
