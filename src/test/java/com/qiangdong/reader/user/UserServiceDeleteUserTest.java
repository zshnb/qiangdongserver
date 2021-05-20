package com.qiangdong.reader.user;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user.DeleteUserRequest;
import com.qiangdong.reader.request.user.ListUserRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceDeleteUserTest extends BaseTest {

	@Autowired
	private UserServiceImpl userService;

	@Test
	public void deleteUserSuccessful(){
		DeleteUserRequest request = new DeleteUserRequest();
		request.setUserId(5L);
		request.setTargetUserId(1L);
		userService.deleteUser(request);
		ListUserRequest listUserRequest = new ListUserRequest();
		listUserRequest.setUserId(5L);
		List<User> data = userService.listUser(listUserRequest).getList();
		assertThat(data.size()).isEqualTo(2);
	}

	@Test
	public void deleteUserFailedWhenPermissionDeny() {
		DeleteUserRequest request = new DeleteUserRequest();
		request.setUserId(userId);
		request.setTargetUserId(1L);
		assertException(PermissionDenyException.class, () -> userService.deleteUser(request));
	}
}
