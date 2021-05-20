package com.qiangdong.reader.request.block_user;

import com.qiangdong.reader.request.BaseRequest;

public class DeleteBlockUserRequest extends BaseRequest {
	private Long blockUserId = 0L;

	public Long getBlockUserId() {
		return blockUserId;
	}

	public void setBlockUserId(Long blockUserId) {
		this.blockUserId = blockUserId;
	}
}
