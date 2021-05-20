package com.qiangdong.reader.validate.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.common.UserConstant;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.user.UserRoleEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.ManagerRegisterRequest;
import com.qiangdong.reader.validate.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class ManagerRegisterValidator extends RequestValidator<ManagerRegisterRequest> {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public void validate(ManagerRegisterRequest request) {
		String cacheCode = redisTemplate.opsForValue()
			.get(String.format(UserConstant.KEY_USER_REGISTER_VERIFY_CODE, request.getMobile()));
		if (!request.getCode().equals(cacheCode)) {
			throw new InvalidArgumentException("短信验证码错误");
		}
		User user = userMapper.selectOne(
			new QueryWrapper<User>()
                .select("id", "role")
                .eq("deleted", 0)
				.eq("mobile", request.getMobile()));
		if (user == null) {
			return;
		}
		if (UserRoleEnum.AUTHOR.equals(user.getRole())) {
			throw new InvalidArgumentException("请不要重复注册!");
		}
	}
}
