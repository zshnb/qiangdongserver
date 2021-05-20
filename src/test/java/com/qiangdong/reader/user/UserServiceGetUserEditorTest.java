package com.qiangdong.reader.user;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user.UserDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.user.GetUserEditorRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceGetUserEditorTest extends BaseTest {

	@Autowired
	private UserServiceImpl userService;

	@Test
	public void getUserEditorSuccessful() {
		GetUserEditorRequest request = new GetUserEditorRequest();
		request.setUserId(adminUserId);
		request.setTargetUserId(4L);
		UserDto data = userService.getUserEditor(request).getData();
		assertThat(data.getId()).isEqualTo(4L);
		assertThat(data.getNickname()).isEqualTo("无缺");
	}

	@Test
	public void getUserEditorFailedWhenStaffNoExist() {
		GetUserEditorRequest request = new GetUserEditorRequest();
		request.setUserId(adminUserId);
		request.setTargetUserId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			userService.getUserEditor(request);
		});
	}

	@Test
	public void getUserEditorFailedWhenPermissionDeny() {
		GetUserEditorRequest request = new GetUserEditorRequest();
		request.setTargetUserId(userId);
		assertException(PermissionDenyException.class, () -> {
			userService.getUserEditor(request);
		});
	}
}
