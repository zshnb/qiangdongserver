package com.qiangdong.reader.enums.notice;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum NoticeTypeEnum implements BaseEnum {
	NONE(0,"NONE"),
	NOTICE_APP(1,"app公告"),
	NOTICE_CREATOR(2,"创作者中心公告")
	;
	@EnumValue
	@JsonValue
	private int code;
	private String description;

	NoticeTypeEnum(int code,String description){
		this.code=code;
		this.description=description;
	}

	@Override
	public int code() {
		return code;
	}

	@Override
	public String toString() {
		return "NoticeTypeEnum{" +
			"code=" + code +
			", description='" + description + '\'' +
			'}';
	}
}
