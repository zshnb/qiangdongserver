package com.qiangdong.reader.enums.comment;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum CommentTypeEnum implements BaseEnum {
	NOVEL(1, "小说"),
	NOVEL_CHAPTER(2, "小说章节"),
	COMIC(3, "漫画"),
	COMIC_CHAPTER(4, "漫画章节"),
	REPLY(5, "回复"),
	USER_ACTIVITY(6, "评论动态")
	;

	@EnumValue
	@JsonValue
	private int code;
	private String description;

	CommentTypeEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public int code() {
		return code;
	}

	@Override
	public String toString() {
		return "CommentTypeEnum{" +
			"code=" + code +
			", description='" + description + '\'' +
			'}';
	}
}
