package com.qiangdong.reader.user;

import static org.mockito.ArgumentMatchers.any;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.UpdateUserInfoRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import com.qiangdong.reader.utils.UserUtil;
import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

public class UserServiceUpdateUserInfoTest extends BaseTest {
	@Autowired
	private UserServiceImpl userService;

	@SpyBean
	private UserUtil userUtil;

	@Before
	public void beforeMock() {
		Mockito.doNothing().when(userUtil).indexUser(any());
	}

	@Test
	public void updateUserInfoSuccessful() {
		UpdateUserInfoRequest request = new UpdateUserInfoRequest();
		request.setUserId(1L);
		request.setAddress("address");
		request.setUsername("new");
		request.setSex(UserSexEnum.MAN);
		userService.updateUserInfo(request);

		User user = userService.getById(1L);
		assertThat(user.getUsername()).isEqualTo("new");
		assertThat(user.getAddress()).isEqualTo("address");

		LocalDateTime lastLoginTime = user.getLastLoginTime();
		userService.updateUserInfo(request);
		user = userService.getById(1L);
		assertThat(user.getLastLoginTime()).isEqualTo(lastLoginTime);
	}

	@Test
	public void updateUserInfoFailedWhenUsernameDuplicate() {
		UpdateUserInfoRequest request = new UpdateUserInfoRequest();
		request.setUserId(1L);
		request.setAddress("address");
		request.setUsername("user2");
		request.setSex(UserSexEnum.MAN);
		assertException(InvalidArgumentException.class, () -> userService.updateUserInfo(request));
	}
}
