package com.qiangdong.reader.enums.message;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.EnumSerializer;
import com.google.gson.annotations.SerializedName;
import com.qiangdong.reader.enums.BaseEnum;

public enum MessageTypeEnum implements BaseEnum {
	NONE(0, ""),
	SYSTEM_MESSAGE(1, "系统消息"),
	AGREE(2, "赞"),
	USER_ACTIVITY_MENTION(3, "动态提到"),
	FOLLOW(4, "粉丝"),
	COMMENT(5, "评论"),
	REPLY(6, "回复"),
	COMMENT_MENTION(7, "评论提到"),
	;

	@JsonValue
	@EnumValue
	private int code;
	private String description;

	MessageTypeEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public int code() {
		return code;
	}
}
