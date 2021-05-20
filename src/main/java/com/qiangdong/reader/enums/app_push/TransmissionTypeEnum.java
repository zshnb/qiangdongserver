package com.qiangdong.reader.enums.app_push;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum TransmissionTypeEnum implements BaseEnum {
	NONE(0, "NONE"),
	NOTIFICATION(1, "通知消息"),
	TRANSMISSION(2, "透传信息")
	;

	@JsonValue
	@EnumValue
	private int code;
	private String description;

	TransmissionTypeEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public int code() {
		return code;
	}
}
