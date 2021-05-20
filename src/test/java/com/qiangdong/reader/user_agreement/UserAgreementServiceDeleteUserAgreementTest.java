package com.qiangdong.reader.user_agreement;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserAgreement;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.user_agreement.DeleteUserAgreementRequest;
import com.qiangdong.reader.serviceImpl.UserAgreementServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAgreementServiceDeleteUserAgreementTest extends BaseTest {

	@Autowired
	private UserAgreementServiceImpl userAgreementService;

	@Test
	public void deleteUserAgreementSuccessful() {
		DeleteUserAgreementRequest request = new DeleteUserAgreementRequest();
		request.setUserId(adminUserId);
		request.setUserAgreementId(1L);
		userAgreementService.deleteUserAgreement(request, new UserAgreement());
		UserAgreement userAgreement = userAgreementService.getById(1L);
		assertThat(userAgreement).isNull();
	}

	@Test
	public void deleteUserAgreementFailedWhenIdIsInvalid() {
		DeleteUserAgreementRequest request = new DeleteUserAgreementRequest();
		request.setUserId(adminUserId);
		request.setUserAgreementId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			userAgreementService.deleteUserAgreement(request, new UserAgreement());
		});
	}

	@Test
	public void deleteUserAgreementFailedWhenPermissionDeny() {
		DeleteUserAgreementRequest request = new DeleteUserAgreementRequest();
		request.setUserAgreementId(1L);
		assertException(PermissionDenyException.class, () -> {
			userAgreementService.deleteUserAgreement(request, new UserAgreement());
		});
	}
}
