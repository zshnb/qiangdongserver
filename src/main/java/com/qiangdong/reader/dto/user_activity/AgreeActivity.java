package com.qiangdong.reader.dto.user_activity;

import com.qiangdong.reader.enums.user_activity.AgreeActivityTypeEnum;
import java.io.Serializable;

public class AgreeActivity implements Serializable {
	private Long commentId = 0L;

	private Long activityId = 0L;

	private AgreeActivityTypeEnum type = AgreeActivityTypeEnum.NONE;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public AgreeActivityTypeEnum getType() {
		return type;
	}

	public void setType(AgreeActivityTypeEnum type) {
		this.type = type;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
}
