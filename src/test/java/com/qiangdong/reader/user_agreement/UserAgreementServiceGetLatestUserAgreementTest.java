package com.qiangdong.reader.user_agreement;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserAgreement;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.serviceImpl.UserAgreementServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAgreementServiceGetLatestUserAgreementTest extends BaseTest {
	@Autowired
	private UserAgreementServiceImpl userAgreementService;

	@Test
	public void getSuccessful() {
		UserAgreement userAgreement = userAgreementService.getLatestUserAgreement(new BaseRequest()).getData();
		assertThat(userAgreement.getContent()).isEqualTo("test content1");
	}
}
