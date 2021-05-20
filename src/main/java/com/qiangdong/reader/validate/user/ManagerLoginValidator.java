package com.qiangdong.reader.validate.user;


import com.qiangdong.reader.common.UserConstant;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.ManagerLoginRequest;
import com.qiangdong.reader.validate.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ManagerLoginValidator extends RequestValidator<ManagerLoginRequest> {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void validate(ManagerLoginRequest request) {
        String cacheCode = redisTemplate.opsForValue().get(UserConstant.KEY_ADMIN_LOGIN_CAPTCHA_CODE + request.getUuid());
        if (!request.getCode().equals(cacheCode)) {
            throw new InvalidArgumentException("验证码错误");
        }
    }
}