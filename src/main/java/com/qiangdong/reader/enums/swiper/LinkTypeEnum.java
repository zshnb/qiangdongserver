package com.qiangdong.reader.enums.swiper;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum LinkTypeEnum implements BaseEnum {
	NONE(0, ""),
	INNER(1, "站内"),
	OUTER(2, "站外")
	;

	LinkTypeEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	@JsonValue
	@EnumValue
	private int code;
	private String description;

	@Override
	public int code() {
		return code;
	}
}
