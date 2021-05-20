package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.user.UserCreatorIdentityEnum;
import com.qiangdong.reader.enums.user.UserRoleEnum;
import com.qiangdong.reader.enums.user.UserSexEnum;
import com.qiangdong.reader.request.user.BecomeAuthorRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceBecomeAuthorTest extends BaseTest {
	@Autowired
	private UserServiceImpl userService;

	@Test
	public void becomeAuthorSuccessful() {
		BecomeAuthorRequest request = new BecomeAuthorRequest();
		request.setUserId(1L);
		request.setAge(11);
		request.setNickname("john");
		request.setSex(UserSexEnum.MAN);
		request.setCreatorIdentity(UserCreatorIdentityEnum.AUTHOR);
		userService.becomeAuthor(request);
		User user = userService.getById(1L);
		assertThat(user.getNickname()).isEqualTo("john");
		assertThat(user.getRole()).isEqualTo(UserRoleEnum.AUTHOR);
		assertThat(user.getCreatorIdentity()).isEqualTo(UserCreatorIdentityEnum.AUTHOR);
	}

	@Test
	public void becomeAuthorAndPainter() {
		BecomeAuthorRequest request = new BecomeAuthorRequest();
		request.setUserId(1L);
		request.setAge(11);
		request.setNickname("john");
		request.setSex(UserSexEnum.MAN);
		request.setCreatorIdentity(UserCreatorIdentityEnum.AUTHOR);
		userService.becomeAuthor(request);
		request.setCreatorIdentity(UserCreatorIdentityEnum.PAINTER);
		userService.becomeAuthor(request);

		User user = userService.getById(1L);
		assertThat(user.getNickname()).isEqualTo("john");
		assertThat(user.getRole()).isEqualTo(UserRoleEnum.AUTHOR);
		assertThat(user.getCreatorIdentity()).isEqualTo(UserCreatorIdentityEnum.AUTHOR_AND_PAINTER);
	}
}
