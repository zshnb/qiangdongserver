package com.qiangdong.reader.enums.goods;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum GoodsTypeEnum implements BaseEnum {
	NONE(0, ""),
	FATE_BOARD(1, "缘签")
	;

	@JsonValue
	@EnumValue
	private int code;
	private String description;

	GoodsTypeEnum(int code, String description) {
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
}
