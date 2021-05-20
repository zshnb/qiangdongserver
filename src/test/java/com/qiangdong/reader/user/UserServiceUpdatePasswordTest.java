package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.common.UserConstant;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.common.VerifyWayEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.UpdateUserPasswordRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;


public class UserServiceUpdatePasswordTest extends BaseTest {

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	public void updatePasswordSuccessful() {
		UpdateUserPasswordRequest request = new UpdateUserPasswordRequest();
		request.setUserId(3L);
		request.setOldPassword("123456");
		request.setNewPassword("123456789");
		userService.updatePassword(request, new User());
		User user = userService.getById(3L);
		assertThat(user.getPassword()).isEqualTo(DigestUtils.md5DigestAsHex("123456789".getBytes()));
	}

	@Test
	public void updatePasswordWhenWrongPassword() {
		UpdateUserPasswordRequest request = new UpdateUserPasswordRequest();
		request.setUserId(3L);
		request.setOldPassword("1234568");
		request.setNewPassword("123456789");
		assertException(InvalidArgumentException.class, () -> {
			userService.updatePassword(request, new User());
		});
	}

	@Test
	public void updatePasswordWhenVerifyCodeNoExist() {
		String key = String.format(UserConstant.KEY_USER_SECURITY_VERIFY_CODE, "13715166404");
		redisTemplate.opsForValue().set(key, String.valueOf("123457"),
				UserConstant.KEY_USER_SECURITY_VERIFY_CODE_TIMEOUT, TimeUnit.SECONDS);
		UpdateUserPasswordRequest request = new UpdateUserPasswordRequest();
		request.setUserId(3L);
		request.setNewPassword("123456789");
		request.setVerifyCode("123456");
		request.setVerifyWay(VerifyWayEnum.MOBILE);
		assertException(InvalidArgumentException.class, () -> {
			userService.updatePassword(request, new User());
		});
	}

}
