package com.qiangdong.reader.validate.user;

import com.qiangdong.reader.common.UserConstant;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.UserFastLoginRequest;
import com.qiangdong.reader.validate.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class FastLoginValidator extends RequestValidator<UserFastLoginRequest> {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void validate(UserFastLoginRequest request) {
        String key = String.format(UserConstant.KEY_USER_FAST_LOGIN_VERIFY_CODE, request.getMobile());
        String cacheCode = redisTemplate.opsForValue().get(key);
        if (!request.getCode().equals(cacheCode)) {
            throw new InvalidArgumentException("短信验证码错误");
        }
    }
}
