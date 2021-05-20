package com.qiangdong.reader.utils;


import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.qiangdong.reader.common.UserConstant;
import com.qiangdong.reader.config.SmsConfig;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.common.VerifyWayEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

@Component
public class VerifyCodeUtil {

    private final IAcsClient iAcsClient;
    private final SmsConfig smsConfig;
    private final EmailUtil emailUtil;
    private final RedisTemplate<String, String> redisTemplate;

    public VerifyCodeUtil(IAcsClient iAcsClient,
                          SmsConfig smsConfig, EmailUtil emailUtil,
                          RedisTemplate<String, String> redisTemplate) {
        this.iAcsClient = iAcsClient;
        this.smsConfig = smsConfig;
        this.emailUtil = emailUtil;
        this.redisTemplate = redisTemplate;
    }

    public int sendPhoneVerifyCode(String mobile) throws ClientException {
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        SendSmsRequest msgRequest = new SendSmsRequest();
        msgRequest.setPhoneNumbers(mobile);
        msgRequest.setSignName(smsConfig.getSignName());
        msgRequest.setTemplateCode(smsConfig.getIdentifyingTempleteCode());
        msgRequest.setTemplateParam("{code:" + code + "}");
        iAcsClient.getAcsResponse(msgRequest);
        return code;
    }


    public int sendEmailVerifyCode(String email) throws MessagingException {
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        String content = String.format("【墙洞】您的验证码 %s,该验证码5分钟内有效", code);
        emailUtil.sendEmail(email, "墙洞安全中心邮箱验证码", content);
        return code;
    }

    public boolean isValidVerifyCode(User user, String verifyCode, VerifyWayEnum verifyWay){
        switch (verifyWay){
            case MOBILE:{
                String key = String.format(UserConstant.KEY_USER_SECURITY_VERIFY_CODE, user.getMobile());
                String cacheCode = redisTemplate.opsForValue().get(key);
                return verifyCode.equals(cacheCode);
            }
            case EMAIL:{
                String key = String.format(UserConstant.KEY_USER_SECURITY_VERIFY_CODE, user.getEmail());
                String cacheCode = redisTemplate.opsForValue().get(key);
                return verifyCode.equals(cacheCode);
            }
            default: throw new InvalidArgumentException("无效的验证方式");
        }
    }

    /**
     * 检查验证码是否还在，防止被恶意刷验证码
     * */
    public void checkVerifyCodeExist(String key) {
        String value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            throw new InvalidArgumentException("验证码发送过于频繁");
        }
    }
}
