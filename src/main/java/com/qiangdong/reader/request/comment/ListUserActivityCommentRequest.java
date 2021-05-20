package com.qiangdong.reader.request.comment;

import com.qiangdong.reader.request.BaseRequest;

public class ListUserActivityCommentRequest extends BaseRequest {
	private Long userActivityId = 0L;

	public Long getUserActivityId() {
		return userActivityId;
	}

	public void setUserActivityId(Long userActivityId) {
		this.userActivityId = userActivityId;
	}
}
