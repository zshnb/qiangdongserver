package com.qiangdong.reader.user;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user.UserDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceGetAuthorInfoTest extends BaseTest {

	@Autowired
	private UserServiceImpl userService;

	@Test
	public void getAuthorInfoSuccessful() {
		BaseRequest request = new BaseRequest();
		request.setUserId(1L);
		UserDto userDto = userService.getAuthorDetail(request).getData();
		assertThat(userDto.getLevelName()).isEqualTo("LV1");
		assertThat(userDto.getNickname()).isEqualTo("author 1");
	}

	@Test
	public void getAuthorInfoWhenAuthorNoExist() {
		BaseRequest request = new BaseRequest();
		request.setUserId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			userService.getAuthorDetail(request);
		});
	}

}
