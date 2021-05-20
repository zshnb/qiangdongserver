package com.qiangdong.reader.enums.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum AuthorWorkStatusEnum implements BaseEnum {
	NONE(0, ""),
	UPDATE(1, "更新"),
	UN_UPDATE(2, "未更新"),
	;
	@EnumValue
	@JsonValue
	private int code;
	private String description;

	AuthorWorkStatusEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public int code() {
		return code;
	}
}
