package com.qiangdong.reader.request.user;

import com.qiangdong.reader.request.BaseRequest;

public class UserLoginRequest extends BaseRequest {
    private String mobile = "";
    private String password = "";
    private String clientId = "";

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
