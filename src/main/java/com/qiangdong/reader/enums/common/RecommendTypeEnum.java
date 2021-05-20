package com.qiangdong.reader.enums.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum RecommendTypeEnum implements BaseEnum {
	NONE(0, ""),
	EDITOR_RECOMMEND(1, "editor-recommend"),
	WALL_RECOMMEND(2, "wall-recommend"),
	NEWCOMER_RECOMMEND(3, "new-comer-recommend"),
	FREE(4, "free"),
	ENDED(5, "ended")
	;
	@EnumValue
	@JsonValue
	private int code;
	private String description;

	RecommendTypeEnum(int code, String description) {
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
