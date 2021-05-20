package com.qiangdong.reader.enums.user_consumption;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum ConsumptionTypeEnum implements BaseEnum {
	NONE(0, ""),
	RECOMMEND_TICKET(1, "推荐票"),
	WALL_TICKET(2, "墙票"),
	WALL_COIN(3, "墙币"),
	;

	@JsonValue
	@EnumValue
	private int code;
	private String description;

	ConsumptionTypeEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public int code() {
		return code;
	}
}
