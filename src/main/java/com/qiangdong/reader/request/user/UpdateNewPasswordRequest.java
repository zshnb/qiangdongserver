package com.qiangdong.reader.request.user;

import com.qiangdong.reader.request.BaseRequest;


public class UpdateNewPasswordRequest extends BaseRequest {
    private String password = "";

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
