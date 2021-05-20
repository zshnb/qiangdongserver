package com.qiangdong.reader.user_prefer_type;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.user.ListUserPreferTypeResponse;
import com.qiangdong.reader.serviceImpl.UserPreferTypeServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserPreferTypeServiceListUserPreferTypeTest extends BaseTest {

	@Autowired
	private UserPreferTypeServiceImpl userPreferTypeService;

	@Test
	public void listUserPreferTypeSuccessful() {
		BaseRequest request = new BaseRequest();
		request.setUserId(1L);
		ListUserPreferTypeResponse response =
			userPreferTypeService.listUserPreferType(request);
		assertThat(response.getUserPreferType().size()).isEqualTo(2);
		assertThat(response.getUserPreferType().get(0).getName()).isEqualTo("分类1");
	}
}
