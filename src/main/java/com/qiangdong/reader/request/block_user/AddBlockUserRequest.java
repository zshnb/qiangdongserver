package com.qiangdong.reader.request.block_user;

import com.qiangdong.reader.request.BaseRequest;

public class AddBlockUserRequest extends BaseRequest {
	private Long targetUserId = 0L;

	public Long getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}
}
