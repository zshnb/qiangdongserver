package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceGetPersonalCenterQrCodeTest extends BaseTest {
	@Autowired
	private UserServiceImpl userService;

	@Test
	public void getPersonalCenterQrCodeSuccessful() {
		BaseRequest request = new BaseRequest();
		request.setUserId(1L);
		String url = userService.getPersonalCenterQrCode(request).getData();
		assertThat(url).isStartWith("www.qiangdong.com/user/1");
	}
}
