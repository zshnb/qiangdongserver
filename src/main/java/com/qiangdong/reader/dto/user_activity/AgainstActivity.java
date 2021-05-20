package com.qiangdong.reader.dto.user_activity;

import java.io.Serializable;

public class AgainstActivity implements Serializable {
	private Long activityId = 0L;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
}
