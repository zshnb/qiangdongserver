package com.qiangdong.reader.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qiangdong.reader.config.JsonLongSerializer;

public class UserIdNameDto {
	@JsonSerialize(using = JsonLongSerializer.class)
	private Long userId;
	private String username;

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
}
