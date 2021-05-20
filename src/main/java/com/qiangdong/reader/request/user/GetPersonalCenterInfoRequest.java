package com.qiangdong.reader.request.user;

import com.qiangdong.reader.request.BaseRequest;

public class GetPersonalCenterInfoRequest extends BaseRequest {
	private Long targetUserId;

	public Long getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}
}
