package com.qiangdong.reader.enums.common;

import com.qiangdong.reader.enums.BaseEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;

public enum LoginPlatformEnum implements BaseEnum {
	APP(1, "app"),
	WEB(2, "web")
	;

	private int code;
	private String description;

	LoginPlatformEnum(int code, String description) {
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

	public static LoginPlatformEnum parseOf(String description) {
		switch (description) {
			case "app": return APP;
			case "web": return WEB;
			default: throw new InvalidArgumentException("不合法的登录平台");
		}
	}
}
