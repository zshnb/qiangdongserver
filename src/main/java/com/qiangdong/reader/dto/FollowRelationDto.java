package com.qiangdong.reader.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.time.LocalDateTime;

public class FollowRelationDto {
	@JsonSerialize(using = ToStringSerializer.class)
	private Long followRelationId;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long followerId;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long followedId;
	private String avatar;
	private String signature;
	private String nickname;
	private Boolean followEach;
	private String username;
	private LocalDateTime createAt;

	public Long getFollowRelationId() {
		return followRelationId;
	}

	public void setFollowRelationId(Long followRelationId) {
		this.followRelationId = followRelationId;
	}

	public Long getFollowerId() {
		return followerId;
	}

	public void setFollowerId(Long followerId) {
		this.followerId = followerId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Boolean getFollowEach() {
		return followEach;
	}

	public void setFollowEach(Boolean followEach) {
		this.followEach = followEach;
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

	public Long getFollowedId() {
		return followedId;
	}

	public void setFollowedId(Long followedId) {
		this.followedId = followedId;
	}
}
