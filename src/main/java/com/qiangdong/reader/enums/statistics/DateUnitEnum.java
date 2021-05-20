package com.qiangdong.reader.enums.statistics;

import com.qiangdong.reader.enums.BaseEnum;

public enum DateUnitEnum implements BaseEnum {
	NONE(0, ""),
	DAY(1, "日"),
	MONTH(2, "月"),
	YEAR(3, "年")
	;

	private int code;
	private String description;

	DateUnitEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public int code() {
		return code;
	}
}
