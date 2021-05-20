package com.qiangdong.reader.enums;

/**
 * 枚举基类
 * */
public interface BaseEnum {
	default int code() {
		return 0;
	}

	default String description() {
		return "";
	}
}
