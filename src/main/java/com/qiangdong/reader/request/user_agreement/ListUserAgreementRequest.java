package com.qiangdong.reader.request.user_agreement;

import com.qiangdong.reader.enums.user_agreement.UserAgreementTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class ListUserAgreementRequest extends BaseRequest {
	private UserAgreementTypeEnum type = UserAgreementTypeEnum.NONE;

	public UserAgreementTypeEnum getType() {
		return type;
	}

	public void setType(UserAgreementTypeEnum type) {
		this.type = type;
	}
}
