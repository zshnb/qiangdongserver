package com.qiangdong.reader.dto.user_activity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qiangdong.reader.entity.Topic;
import com.qiangdong.reader.enums.user_activity.ActivityTypeEnum;
import java.time.LocalDateTime;
import java.util.List;

public class UserActivityDto {
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	private String username;
	private ActivityData activityData;
	private Boolean top;
	private LocalDateTime createAt;
	private List<Topic> topics;
	private String avatar;
	private Boolean isFollow = false;
	private Boolean isAgree = false;
	private Boolean isAgainst = false;
	private Boolean isInCollection = false;
	private ActivityTypeEnum type;

	public Boolean getAgree() {
		return isAgree;
	}

	public void setAgree(Boolean agree) {
		isAgree = agree;
	}

	public Boolean getAgainst() {
		return isAgainst;
	}

	public void setAgainst(Boolean against) {
		isAgainst = against;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public ActivityData getActivityData() {
		return activityData;
	}

	public void setActivityData(ActivityData activityData) {
		this.activityData = activityData;
	}

	public Boolean getTop() {
		return top;
	}

	public void setTop(Boolean top) {
		this.top = top;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Boolean getFollow() {
		return isFollow;
	}

	public void setFollow(Boolean follow) {
		isFollow = follow;
	}

	public Boolean getInCollection() {
		return isInCollection;
	}

	public void setInCollection(Boolean inCollection) {
		isInCollection = inCollection;
	}

	public ActivityTypeEnum getType() {
		return type;
	}

	public void setType(ActivityTypeEnum type) {
		this.type = type;
	}
}
