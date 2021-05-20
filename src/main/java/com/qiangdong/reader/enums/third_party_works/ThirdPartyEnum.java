package com.qiangdong.reader.enums.third_party_works;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum ThirdPartyEnum implements BaseEnum {
	NONE(0, ""),
	SI_WEI(1, "四维文学")
	;

	@JsonValue
	@EnumValue
	int code;
	String description;

	ThirdPartyEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public int code() {
		return code;
	}
}
