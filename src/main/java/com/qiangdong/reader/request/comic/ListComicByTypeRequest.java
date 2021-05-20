package com.qiangdong.reader.request.comic;

import com.qiangdong.reader.request.BaseRequest;

public class ListComicByTypeRequest extends BaseRequest {
	private Long typeId = 0L;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
}
