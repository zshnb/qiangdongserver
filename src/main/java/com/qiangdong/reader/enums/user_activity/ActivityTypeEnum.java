package com.qiangdong.reader.enums.user_activity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum ActivityTypeEnum implements BaseEnum {
	NONE(0, ""),
	PUBLISH_ACTIVITY(1, "发表动态"),
	AGREE_ACTIVITY(2, "赞同动态"),
	AGAINST_ACTIVITY(3, "反对动态"),
	SHARE_ACTIVITY(4, "转发动态")
	;

	@JsonValue
	@EnumValue
	private int code;
	private String description;

	ActivityTypeEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public int code() {
		return code;
	}
}
