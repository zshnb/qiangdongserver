package com.qiangdong.reader.request.user_prefer_type;

import com.qiangdong.reader.request.BaseRequest;

public class DeleteUserPreferTypeRequest extends BaseRequest {
	private Long userPreferTypeId = 0L;

	public Long getUserPreferTypeId() {
		return userPreferTypeId;
	}

	public void setUserPreferTypeId(Long userPreferTypeId) {
		this.userPreferTypeId = userPreferTypeId;
	}
}
