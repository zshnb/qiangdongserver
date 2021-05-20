package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user.UserIdNameDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.CheckUserRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceCheckUserExistTest extends BaseTest {
	@Autowired
	private UserServiceImpl userService;

	@Test
	public void checkUserExistWhenUserExist() {
		CheckUserRequest request = new CheckUserRequest();
		request.setTargetUserId(1L);
		UserIdNameDto userIdNameDto = userService.checkUserExist(request).getData();
		assertThat(userIdNameDto.getUserId()).isEqualTo(1L);
	}

	@Test
	public void checkUserExistWhenNotExist() {
		CheckUserRequest request = new CheckUserRequest();
		request.setTargetUserId(-1L);
		assertException(InvalidArgumentException.class, () -> userService.checkUserExist(request));
	}
}
