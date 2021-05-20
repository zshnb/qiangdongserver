package com.qiangdong.reader.request.user_credit_record;

import com.qiangdong.reader.request.BaseRequest;

public class ListUserCreditRecordRequest extends BaseRequest {
    private String username =  "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
