package com.qiangdong.reader.user_agreement;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserAgreement;
import com.qiangdong.reader.enums.user_agreement.UserAgreementTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.user_agreement.GetUserAgreementByTypeRequest;
import com.qiangdong.reader.request.user_agreement.GetUserAgreementRequest;
import com.qiangdong.reader.serviceImpl.UserAgreementServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAgreementServiceGetUserAgreementByTypeTest extends BaseTest {

	@Autowired
	private UserAgreementServiceImpl userAgreementService;

	@Test
	public void getUserAgreementSuccessful() {
		GetUserAgreementByTypeRequest request = new GetUserAgreementByTypeRequest();
		request.setType(UserAgreementTypeEnum.USER_AGREEMENT);
		UserAgreement userAgreement = userAgreementService.getUserAgreementByType(request).getData();
		assertThat(userAgreement).isNotNull();
		assertThat(userAgreement.getVersion()).isEqualTo("test name1");
		assertThat(userAgreement.getContent()).isEqualTo("test content1");
	}
}
