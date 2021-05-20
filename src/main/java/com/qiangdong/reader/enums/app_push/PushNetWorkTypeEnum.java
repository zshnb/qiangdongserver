package com.qiangdong.reader.enums.app_push;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum PushNetWorkTypeEnum implements BaseEnum {
	DEFAULT(0, "无限制"),
	WIFI(1, "链接wifi下发推送")
	;

	@JsonValue
	@EnumValue
	private int code;
	private String description;

	PushNetWorkTypeEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public int code() {
		return code;
	}
}
