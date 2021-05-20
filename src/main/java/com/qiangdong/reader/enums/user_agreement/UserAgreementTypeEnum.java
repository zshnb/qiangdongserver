package com.qiangdong.reader.enums.user_agreement;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum UserAgreementTypeEnum implements BaseEnum {
	NONE(0, "NONE"),
	USER_AGREEMENT(1, "用户协议"),
	PRIVACY_AGREEMENT(2, "隐私协议");

	@JsonValue
	@EnumValue
	private int code;
	private String description;

	UserAgreementTypeEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public int code() {
		return code;
	}

}
