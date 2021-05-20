package com.qiangdong.reader.request.user_agreement;

import com.qiangdong.reader.request.BaseRequest;

public class GetUserAgreementRequest extends BaseRequest {

	private Long userAgreementId = 0L;

	public Long getUserAgreementId() {
		return userAgreementId;
	}

	public void setUserAgreementId(Long userAgreementId) {
		this.userAgreementId = userAgreementId;
	}
}
