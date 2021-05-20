package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user.UserAuthorDto;
import com.qiangdong.reader.enums.user.UserCreatorIdentityEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceListAuthorTest extends BaseTest {

	@Autowired
	private UserServiceImpl userService;

	@Test
	public void listAuthorSuccessful(){
		BaseRequest request = new BaseRequest();
		request.setUserId(adminUserId);
		List<UserAuthorDto> data = userService.listAuthor(request).getList();
		assertThat(data.size()).isEqualTo(2);
		assertThat(data.get(0).getIdentity()).isEqualTo(UserCreatorIdentityEnum.AUTHOR_AND_PAINTER);
	}

	@Test
	public void listAuthorWhenUserNoPower(){
		BaseRequest request = new BaseRequest();
		request.setUserId(3L);
		assertException(PermissionDenyException.class, ()->{
			userService.listAuthor(request);
		});
	}
}
