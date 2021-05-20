package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.user.UpdateAuthorByEditorRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceUpdateAuthorByEditorTest extends BaseTest {

	@Autowired
	private UserServiceImpl userService;

	@Test
	public void updateAuthorSuccessful() {
		User author = userService.getById(userId);
		UpdateAuthorByEditorRequest request = new UpdateAuthorByEditorRequest();
		request.setUserId(editorUserId);
		BeanUtils.copyProperties(author, request);
		request.setTargetUserId(userId);
		request.setLevelId(2L);
		User targetUser = userService.updateAuthorByEditor(request).getData();
		assertThat(targetUser.getLevelId()).isEqualTo(2L);
	}

	@Test
	public void updateAuthorFailedWhenUserPermissionDeny() {
		UpdateAuthorByEditorRequest request = new UpdateAuthorByEditorRequest();
		request.setUserId(userId);
		assertException(PermissionDenyException.class, () -> {
			userService.updateAuthorByEditor(request);
		});
	}

	@Test
	public void updateAuthorFailedWhenUserNoExist() {
		UpdateAuthorByEditorRequest request = new UpdateAuthorByEditorRequest();
		request.setUserId(editorUserId);
		request.setTargetUserId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			userService.updateAuthorByEditor(request);
		});
	}

	@Test
	public void updateAuthorFailedWhenNicknameExist() {
		UpdateAuthorByEditorRequest request = new UpdateAuthorByEditorRequest();
		request.setUserId(editorUserId);
		request.setTargetUserId(1L);
		request.setNickname("author 2");
		assertException(InvalidArgumentException.class, () -> {
			userService.updateAuthorByEditor(request);
		});
	}
}
