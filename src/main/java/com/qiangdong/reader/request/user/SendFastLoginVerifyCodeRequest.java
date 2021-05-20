package com.qiangdong.reader.request.user;

import com.qiangdong.reader.request.BaseRequest;

public class SendFastLoginVerifyCodeRequest extends BaseRequest {
    private String mobile = "";

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
