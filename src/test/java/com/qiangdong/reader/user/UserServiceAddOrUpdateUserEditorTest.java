package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.AddOrUpdateUserEditorRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import java.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;


public class UserServiceAddOrUpdateUserEditorTest extends BaseTest {

	@Autowired
	private UserServiceImpl userService;

	@Test
	public void addUserEditorSuccessful() {
		AddOrUpdateUserEditorRequest request = new AddOrUpdateUserEditorRequest();
		request.setTypeId(1L);
		request.setMobile("13715166404");
		request.setSex(UserSexEnum.MAN);
		request.setBirthday(LocalDateTime.now());
		User user = userService.addOrUpdateUserEditor(request).getData();
		Assert.assertNotEquals(0L, user.getId().longValue());
		Assert.assertEquals("13715166404", user.getMobile());
	}

	@Test
	public void updateUserEditorSuccessful() {
		String password = "123456789";
		AddOrUpdateUserEditorRequest request = new AddOrUpdateUserEditorRequest();
		request.setEditorId(4L);
		request.setTypeId(3L);
		request.setPassword(password);
		userService.addOrUpdateUserEditor(request);
		User user = userService.getById(4L);
		Assert.assertEquals(DigestUtils.md5DigestAsHex(password.getBytes()), user.getPassword());
	}

	@Test
	public void addUserEditorWhenTypeNoExist() {
		AddOrUpdateUserEditorRequest request = new AddOrUpdateUserEditorRequest();
		request.setTypeId(-1L);
		request.setMobile("13715166404");
		assertException(InvalidArgumentException.class, () -> {
			userService.addOrUpdateUserEditor(request);
		});
	}

}
