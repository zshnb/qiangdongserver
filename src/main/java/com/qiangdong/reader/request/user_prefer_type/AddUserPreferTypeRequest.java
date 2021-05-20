package com.qiangdong.reader.request.user_prefer_type;


import com.qiangdong.reader.request.BaseRequest;

public class AddUserPreferTypeRequest extends BaseRequest {
	private Long typeId = 0L;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
}
