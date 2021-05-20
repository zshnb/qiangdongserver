package com.qiangdong.reader.enums.fate_board;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum FateBoardStatusEnum implements BaseEnum {
	NONE(0, ""),
	UP(1, "挂上"),
	DOWN(2, "卸下")
	;

	@JsonValue
	@EnumValue
	private int code;
	private String description;

	FateBoardStatusEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public int code() {
		return code;
	}
}
