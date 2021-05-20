package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user.SecrecyConfig;
import com.qiangdong.reader.enums.user.SecrecyConfigUserPermissionTypeEnum;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user.UpdateSecrecyConfigRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceUpdateSecrecyConfigTest extends BaseTest {
	@Autowired
	private UserServiceImpl userService;

	@Test
	public void updateSecrecyConfigSuccessful() {
		SecrecyConfig secrecyConfig = new SecrecyConfig();
		secrecyConfig.setEnableAddressBookRecommend(true);
		secrecyConfig.setEnableSearchByMobile(true);
		UpdateSecrecyConfigRequest request = new UpdateSecrecyConfigRequest();
		request.setUserId(1L);
		request.setSecrecyConfig(secrecyConfig);
		userService.updateSecrecyConfig(request);

		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserId(1L);
		secrecyConfig = userService.getSecrecyConfig(baseRequest).getData();
		assertThat(secrecyConfig.isEnableAddressBookRecommend()).isTrue();
		assertThat(secrecyConfig.getWatchMyFans()).isEqualTo(SecrecyConfigUserPermissionTypeEnum.ALL);
	}
}
