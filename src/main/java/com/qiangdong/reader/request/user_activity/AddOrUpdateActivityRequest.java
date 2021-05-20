package com.qiangdong.reader.request.user_activity;

import com.qiangdong.reader.annotation.NotNone;
import com.qiangdong.reader.dto.user_activity.ActivityData;
import com.qiangdong.reader.enums.user_activity.ActivityTypeEnum;
import com.qiangdong.reader.request.BaseRequest;
import java.util.ArrayList;
import java.util.List;

public class AddOrUpdateActivityRequest extends BaseRequest {
	private Long activityId = 0L;
	@NotNone(message = "无效的动态类型")
	private ActivityTypeEnum type = ActivityTypeEnum.NONE;
	private ActivityData activityData = new ActivityData();
	private Boolean top = false;
	private List<Long> topicIds = new ArrayList<>();

	public ActivityTypeEnum getType() {
		return type;
	}

	public void setType(ActivityTypeEnum type) {
		this.type = type;
	}

	public ActivityData getActivityData() {
		return activityData;
	}

	public void setActivityData(ActivityData activityData) {
		this.activityData = activityData;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public Boolean getTop() {
		return top;
	}

	public void setTop(Boolean top) {
		this.top = top;
	}

	public List<Long> getTopicIds() {
		return topicIds;
	}

	public void setTopicIds(List<Long> topicIds) {
		this.topicIds = topicIds;
	}
}
