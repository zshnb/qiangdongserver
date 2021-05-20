package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user.SecrecyConfig;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceGetUserSecrecyConfigTest extends BaseTest {
	@Autowired
	private UserServiceImpl userService;

	@Test
	public void getSecrecyConfigSuccessful() {
		BaseRequest request = new BaseRequest();
		request.setUserId(1L);
		SecrecyConfig secrecyConfig = userService.getSecrecyConfig(request).getData();
		assertThat(secrecyConfig.isEnableSearchByMobile()).isNotTrue();
	}
}
