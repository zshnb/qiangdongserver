package com.qiangdong.reader.request.function_area;

import com.qiangdong.reader.request.BaseRequest;

public class ListByParentFunctionAreaRequest extends BaseRequest {
	private Long parentId = 0L;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
