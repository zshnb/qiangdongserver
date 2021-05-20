package com.qiangdong.reader.request.follow_relation;

import com.qiangdong.reader.request.BaseRequest;

public class ListFollowUserRequest extends BaseRequest {
	private Long targetUserId = 0L;

	public Long getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}
}
