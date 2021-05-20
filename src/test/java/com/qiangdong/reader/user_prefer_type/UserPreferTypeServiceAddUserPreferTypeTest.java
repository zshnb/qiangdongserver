package com.qiangdong.reader.user_prefer_type;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.UserPreferType;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_prefer_type.AddUserPreferTypeRequest;
import com.qiangdong.reader.serviceImpl.UserPreferTypeServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class UserPreferTypeServiceAddUserPreferTypeTest extends BaseTest {

	@Autowired
	private UserPreferTypeServiceImpl userPreferTypeService;

	@Test
	public void addUserPreferTypeSuccessful() {
		AddUserPreferTypeRequest request = new AddUserPreferTypeRequest();
		request.setUserId(1L);
		request.setTypeId(3L);
		UserPreferType userPreferType =
			userPreferTypeService.addUserPreferType(request, new Type()).getData();
		assertThat(userPreferType.getUserId()).isEqualTo(1L);
		assertThat(userPreferType.getTypeId()).isEqualTo(3L);
	}

	@Test
	public void addUserPreferTypeWhenTypeNoExist() {
		AddUserPreferTypeRequest request = new AddUserPreferTypeRequest();
		request.setUserId(1L);
		request.setTypeId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			userPreferTypeService.addUserPreferType(request, new Type());
		});
	}

}
