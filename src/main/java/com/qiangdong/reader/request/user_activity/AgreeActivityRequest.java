package com.qiangdong.reader.request.user_activity;

import com.qiangdong.reader.request.BaseRequest;

public class AgreeActivityRequest extends BaseRequest {
	private Long activityId = 0L;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
}
