package com.qiangdong.reader.user_prefer_type;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserPreferType;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_prefer_type.DeleteUserPreferTypeRequest;
import com.qiangdong.reader.response.user.ListUserPreferTypeResponse;
import com.qiangdong.reader.serviceImpl.UserPreferTypeServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserPreferTypeServiceDeleteUserPreferTypeTest extends BaseTest {

	@Autowired
	private UserPreferTypeServiceImpl userPreferTypeService;

	@Test
	public void deleteUserPreferTypeSuccessful(){
		DeleteUserPreferTypeRequest request = new DeleteUserPreferTypeRequest();
		request.setUserId(1L);
		request.setUserPreferTypeId(1L);
		userPreferTypeService.deleteUserPreferType(request, new UserPreferType());
		BaseRequest listUserPreferTypeRequest = new BaseRequest();
		listUserPreferTypeRequest.setUserId(1L);
		ListUserPreferTypeResponse response = userPreferTypeService.listUserPreferType(listUserPreferTypeRequest);
		assertThat(response.getUserPreferType().size()).isEqualTo(1);
	}

	@Test
	public void deleteUserPreferTypeWhenTypeNoExist(){
		DeleteUserPreferTypeRequest request = new DeleteUserPreferTypeRequest();
		request.setUserId(1L);
		request.setUserPreferTypeId(-1L);
		assertException(InvalidArgumentException.class, ()->{
			userPreferTypeService.deleteUserPreferType(request, new UserPreferType());
		});
	}

}
