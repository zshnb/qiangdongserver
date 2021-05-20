package com.qiangdong.reader.request.user;

import com.qiangdong.reader.request.BaseRequest;

public class UserFastLoginRequest extends BaseRequest {
    private String mobile = "";
    private String code = "";
    private String clientId = "";

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
