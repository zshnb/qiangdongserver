package com.qiangdong.reader.enums.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum WorksUpdateStatusEnum implements BaseEnum {
	NONE(0, "未知"),
	UPDATING(1, "连载中"),
	END(2, "已完结");

	@EnumValue
	@JsonValue
	private int code;
	private String description;

	WorksUpdateStatusEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public int code() {
		return code;
	}

	@Override
	public String description() {
		return description;
	}


	@Override
	public String toString() {
		return "WorksUpdateStatusEnum{" +
			"code=" + code +
			", description='" + description + '\'' +
			'}';
	}

}
