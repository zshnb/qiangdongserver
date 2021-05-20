package com.qiangdong.reader.request.function_area;

import com.qiangdong.reader.request.BaseRequest;

public class DeleteFunctionAreaRequest extends BaseRequest {
	private Long functionAreaId = 0L;

	public Long getFunctionAreaId() {
		return functionAreaId;
	}

	public void setFunctionAreaId(Long functionAreaId) {
		this.functionAreaId = functionAreaId;
	}
}
