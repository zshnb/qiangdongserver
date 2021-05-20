package com.qiangdong.reader.request.user;


import com.qiangdong.reader.request.BaseRequest;

public class ManagerLoginRequest extends BaseRequest {

    private String account = "";
    private String password = "";
    private String code = "";
    private String uuid = "";


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
