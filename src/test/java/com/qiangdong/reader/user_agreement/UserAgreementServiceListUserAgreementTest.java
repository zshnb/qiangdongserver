package com.qiangdong.reader.user_agreement;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserAgreement;
import com.qiangdong.reader.enums.user_agreement.UserAgreementTypeEnum;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_agreement.ListUserAgreementRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.UserAgreementServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAgreementServiceListUserAgreementTest extends BaseTest {

	@Autowired
	private UserAgreementServiceImpl userAgreementService;

	@Test
	public void listUserAgreementSuccessful() {
		ListUserAgreementRequest request = new ListUserAgreementRequest();
		request.setType(UserAgreementTypeEnum.USER_AGREEMENT);
		PageResponse<UserAgreement> pageResponse = userAgreementService.listUserAgreement(request);
		assertThat(pageResponse.getList().size()).isEqualTo(1);
	}
}
