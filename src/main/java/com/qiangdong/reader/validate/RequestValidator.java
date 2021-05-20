package com.qiangdong.reader.validate;

import com.qiangdong.reader.request.BaseRequest;

abstract public class RequestValidator<T extends BaseRequest> {
	public abstract void validate(T request);
}
