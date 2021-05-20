package com.qiangdong.reader.validate.user;

import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.SendMessageRegisterRequest;
import com.qiangdong.reader.utils.UserUtil;
import com.qiangdong.reader.validate.RequestValidator;
import org.springframework.stereotype.Component;

@Component
public class RegisterVerifyCodeValidator extends RequestValidator<SendMessageRegisterRequest> {

    private final UserUtil userUtil;

    public RegisterVerifyCodeValidator(UserUtil userUtil) {
        this.userUtil = userUtil;
    }

    @Override
    public void validate(SendMessageRegisterRequest request) {
        if (!userUtil.isValidMobile(request.getMobile())) {
            throw new InvalidArgumentException("无效的手机格式");
        }
    }
}
