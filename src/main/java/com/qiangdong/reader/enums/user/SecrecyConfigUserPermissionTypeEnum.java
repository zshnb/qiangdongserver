package com.qiangdong.reader.enums.user;

import com.qiangdong.reader.enums.BaseEnum;

public enum SecrecyConfigUserPermissionTypeEnum implements BaseEnum {
	NONE(0, ""),
	ALL(1, "所有人"),
	MY_FOLLOW(2, "我关注的"),
	FANS(3, "粉丝")
	;

	private int code;
	private String description;

	SecrecyConfigUserPermissionTypeEnum(int code, String description) {
		this.code = code;
		this.description = description;
	}

	@Override
	public int code() {
		return code;
	}
}
