package com.qiangdong.reader.user_agreement;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserAgreement;
import com.qiangdong.reader.enums.user_agreement.UserAgreementTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.serviceImpl.UserAgreementServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAgreementServiceAddOrUpdateUserAgreementTest extends BaseTest {

	@Autowired
	private UserAgreementServiceImpl userAgreementService;

	@Test
	public void addUserAgreementSuccessful() {
		UserAgreement userAgreement = userAgreementService
			.addOrUpdateUserAgreement(addOrUpdateUserAgreementRequest)
			.getData();
		assertThat(userAgreement.getContent()).isEqualTo("test content");
		assertThat(userAgreement.getVersion()).isEqualTo("test versionName");
		assertThat(userAgreement.getType()).isEqualTo(UserAgreementTypeEnum.USER_AGREEMENT);
	}

	@Test
	public void updateUserAgreementSuccessful() {
		addOrUpdateUserAgreementRequest.setUserAgreementId(1L);
		UserAgreement userAgreement = userAgreementService
			.addOrUpdateUserAgreement(addOrUpdateUserAgreementRequest)
			.getData();
		assertThat(userAgreement.getContent()).isEqualTo("test content");
		assertThat(userAgreement.getVersion()).isEqualTo("test versionName");
		assertThat(userAgreement.getType()).isEqualTo(UserAgreementTypeEnum.USER_AGREEMENT);
	}

	@Test
	public void updateUserAgreementFailedWhenIdIsInvalid() {
		addOrUpdateUserAgreementRequest.setUserAgreementId(100L);
		assertException(InvalidArgumentException.class, () -> {
			userAgreementService.addOrUpdateUserAgreement(addOrUpdateUserAgreementRequest);
		});
	}

	@Test
	public void addUserAgreementFailedWhenPermissionDeny() {
		addOrUpdateUserAgreementRequest.setUserId(authorUserId);
		assertException(PermissionDenyException.class, () -> {
			userAgreementService.addOrUpdateUserAgreement(addOrUpdateUserAgreementRequest);
		});
	}
}
