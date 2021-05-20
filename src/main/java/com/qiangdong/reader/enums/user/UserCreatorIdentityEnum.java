package com.qiangdong.reader.enums.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum UserCreatorIdentityEnum implements BaseEnum {

	NONE(0, ""),
	AUTHOR(1, "作家"),
	PAINTER(2, "画家"),
	AUTHOR_AND_PAINTER(3, "作家和画家")
	;

	@EnumValue
	@JsonValue
	private int code;
	private String description;

	UserCreatorIdentityEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public int code() {
		return code;
	}
}
