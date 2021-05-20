package com.qiangdong.reader.enums.message;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum MessageReadStatusEnum implements BaseEnum {
	NONE(0, ""),
	READ(1, "已读"),
	UNREAD(2, "未读")
	;

	MessageReadStatusEnum(int code, String description) {
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
