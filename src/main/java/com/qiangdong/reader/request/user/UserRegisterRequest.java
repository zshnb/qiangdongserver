package com.qiangdong.reader.request.user;

import com.qiangdong.reader.request.BaseRequest;

public class UserRegisterRequest  extends BaseRequest {
    private String mobile = "";
    private String password = "";
    private String code = "";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

}
