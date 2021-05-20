package com.qiangdong.reader.request.user;

import com.qiangdong.reader.request.BaseRequest;

public class GetUserEditorRequest extends BaseRequest {

	private Long targetUserId = 0L;

	public Long getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}
}
