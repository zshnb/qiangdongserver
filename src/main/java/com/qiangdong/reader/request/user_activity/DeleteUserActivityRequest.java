package com.qiangdong.reader.request.user_activity;


import com.qiangdong.reader.request.BaseRequest;
import java.util.ArrayList;
import java.util.List;

public class DeleteUserActivityRequest extends BaseRequest {
	private List<Long> activityIds = new ArrayList<>();

	public List<Long> getActivityIds() {
		return activityIds;
	}

	public void setActivityIds(List<Long> activityIds) {
		this.activityIds = activityIds;
	}
}
