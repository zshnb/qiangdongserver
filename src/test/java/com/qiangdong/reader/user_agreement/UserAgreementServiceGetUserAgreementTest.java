package com.qiangdong.reader.user_agreement;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserAgreement;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.user_agreement.GetUserAgreementRequest;
import com.qiangdong.reader.serviceImpl.UserAgreementServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAgreementServiceGetUserAgreementTest extends BaseTest {

	@Autowired
	private UserAgreementServiceImpl userAgreementService;

	@Test
	public void getUserAgreementSuccessful() {
		GetUserAgreementRequest request = new GetUserAgreementRequest();
		request.setUserId(adminUserId);
		request.setUserAgreementId(1L);
		UserAgreement userAgreement = userAgreementService.getUserAgreement(request,new UserAgreement()).getData();
		assertThat(userAgreement).isNotNull();
		assertThat(userAgreement.getVersion()).isEqualTo("test name1");
		assertThat(userAgreement.getContent()).isEqualTo("test content1");
	}

	@Test
	public void getUserAgreementFailedWhenIsInvalid() {
		GetUserAgreementRequest request = new GetUserAgreementRequest();
		request.setUserAgreementId(-1L);
		request.setUserId(adminUserId);
		assertException(InvalidArgumentException.class, () -> {
			userAgreementService.getUserAgreement(request,new UserAgreement());
		});
	}

	@Test
	public void getUserAgreementFailedWhenPermissionDeny() {
		GetUserAgreementRequest request = new GetUserAgreementRequest();
		request.setUserAgreementId(1L);
		request.setUserId(authorUserId);
		assertException(PermissionDenyException.class, () -> {
			userAgreementService.getUserAgreement(request,new UserAgreement());
		});
	}
}
