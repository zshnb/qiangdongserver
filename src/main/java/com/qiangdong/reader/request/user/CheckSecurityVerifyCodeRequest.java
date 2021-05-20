package com.qiangdong.reader.request.user;

import com.qiangdong.reader.enums.common.VerifyWayEnum;
import com.qiangdong.reader.request.BaseRequest;

public class CheckSecurityVerifyCodeRequest  extends BaseRequest {

    private VerifyWayEnum verifyWay = VerifyWayEnum.NONE;
    private String verifyCode = "";

    public VerifyWayEnum getVerifyWay() {
        return verifyWay;
    }

    public void setVerifyWay(VerifyWayEnum verifyWay) {
        this.verifyWay = verifyWay;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
