package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user.UserDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.GetPersonalCenterInfoRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceGetPersonalCenterInfoTest extends BaseTest {
	@Autowired
	private UserServiceImpl userService;

	@Test
	public void getSelfPersonalCenterInfoSuccessful() {
		GetPersonalCenterInfoRequest request = new GetPersonalCenterInfoRequest();
		request.setUserId(1L);
		request.setTargetUserId(1L);
		UserDto userDto = userService.getPersonalCenterInfo(request).getData();
		assertThat(userDto.getId()).isGreaterThan(0L);
		assertThat(userDto.getFollow()).isNotTrue();
	}

	@Test
	public void getOtherPersonalCenterInfoSuccessful() {
		GetPersonalCenterInfoRequest request = new GetPersonalCenterInfoRequest();
		request.setUserId(1L);
		request.setTargetUserId(2L);
		UserDto userDto = userService.getPersonalCenterInfo(request).getData();
		assertThat(userDto.getId()).isGreaterThan(0L);
		assertThat(userDto.getFollow()).isTrue();

		request.setTargetUserId(3L);
		userDto = userService.getPersonalCenterInfo(request).getData();
		assertThat(userDto.getId()).isGreaterThan(0L);
		assertThat(userDto.getFollow()).isNotTrue();
	}

	@Test
	public void getPersonalCenterInfoFailedWhenUserNotExist() {
		GetPersonalCenterInfoRequest request = new GetPersonalCenterInfoRequest();
		request.setTargetUserId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			userService.getPersonalCenterInfo(request);
		});
	}
}
