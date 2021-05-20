package com.qiangdong.reader.request.user;


import com.qiangdong.reader.request.BaseRequest;
 
public class CaptchaLoginRequest extends BaseRequest {

    private String uuid = "";

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
