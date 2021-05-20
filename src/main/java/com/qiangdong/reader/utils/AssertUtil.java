package com.qiangdong.reader.utils;

import com.qiangdong.reader.exception.InvalidArgumentException;

public class AssertUtil {
	public static void assertNotNull(Object o, String message) {
		if (o == null) {
			throw new InvalidArgumentException(message);
		}
	}

	public static void assertNull(Object o, String message) {
		if (o != null) {
			throw new InvalidArgumentException(message);
		}
	}
}
