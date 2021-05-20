package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.UpdateUserEmailRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceUpdateEmailTest extends BaseTest {

	@Autowired
	private UserServiceImpl userService;

	@Test
	public void updateEmailSuccessful() {
		UpdateUserEmailRequest request = new UpdateUserEmailRequest();
		request.setUserId(1L);
		request.setEmail("xxxx.xxxxx@xxx.com");
		userService.updateEmail(request, new User());
		User user = userService.getById(1L);
		assertThat(user.getEmail()).isEqualTo("xxxx.xxxxx@xxx.com");
	}

	@Test
	public void updateEmailWhenWrongEmail() {
		UpdateUserEmailRequest request = new UpdateUserEmailRequest();
		request.setUserId(1L);
		request.setEmail("xxxx.-——@xxx.com");
		assertException(InvalidArgumentException.class, () -> {
			userService.updateEmail(request, new User());
		});
	}

}
