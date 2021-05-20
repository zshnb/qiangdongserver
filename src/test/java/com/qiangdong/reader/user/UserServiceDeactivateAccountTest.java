package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceDeactivateAccountTest extends BaseTest {
	@Autowired
	private UserServiceImpl userService;

	@Test
	public void deactivateAccountSuccessful(){
		BaseRequest request = new BaseRequest();
		request.setUserId(1L);
		userService.deactivateAccount(request);
		User user = userService.getById(1L);
		assertThat(user).isNull();
	}
}
