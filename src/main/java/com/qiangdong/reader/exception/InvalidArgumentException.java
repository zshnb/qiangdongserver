package com.qiangdong.reader.exception;

/**
 * This exception will be throw when request is not valid, such as repeat value or exceed limit
 * */
public class InvalidArgumentException extends RuntimeException {
	public InvalidArgumentException(String message) {
		super(message);
	}
}
