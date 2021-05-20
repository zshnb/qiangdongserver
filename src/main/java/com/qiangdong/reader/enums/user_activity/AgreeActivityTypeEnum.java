package com.qiangdong.reader.enums.user_activity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum AgreeActivityTypeEnum implements BaseEnum {
	NONE(0, ""),
	USER_ACTIVITY(1, "动态"),
	COMMENT(2, "评论")
	;

	AgreeActivityTypeEnum(int code, String description) {
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
