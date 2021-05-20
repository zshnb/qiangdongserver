package com.qiangdong.reader.validate.user;

import com.qiangdong.reader.utils.UserUtil;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.SendFastLoginVerifyCodeRequest;
import com.qiangdong.reader.validate.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FastLoginVerifyCodeValidator extends RequestValidator<SendFastLoginVerifyCodeRequest> {

    private final UserUtil userUtil;

    public FastLoginVerifyCodeValidator(UserUtil userUtil) {
        this.userUtil = userUtil;
    }

    @Override
    public void validate(SendFastLoginVerifyCodeRequest request) {
        if (!userUtil.isValidMobile(request.getMobile())) {
            throw new InvalidArgumentException("请确认手机格式");
        }
    }
}
