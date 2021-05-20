package com.qiangdong.reader.dto.comment;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.qiangdong.reader.config.JsonLongSerializer;
import com.qiangdong.reader.enums.comment.CommentTypeEnum;
import java.time.LocalDateTime;

public class CommentDto {
	@JsonSerialize(using = JsonLongSerializer.class)
	private Long id;
	@JsonSerialize(using = JsonLongSerializer.class)
	private Long userId;
	private String username;
	private String content;
	private String avatar;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createAt;
	private Integer agreeCount;
	private Integer againstCount;
	private CommentTypeEnum type;
	private Long associateId;
	private String images;

	public Long getAssociateId() {
		return associateId;
	}

	public void setAssociateId(Long associateId) {
		this.associateId = associateId;
	}

	public Integer getAgreeCount() {
		return agreeCount;
	}

	public void setAgreeCount(Integer agreeCount) {
		this.agreeCount = agreeCount;
	}

	public Integer getAgainstCount() {
		return againstCount;
	}

	public void setAgainstCount(Integer againstCount) {
		this.againstCount = againstCount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CommentTypeEnum getType() {
		return type;
	}

	public void setType(CommentTypeEnum type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "CommentDto{" +
				"id=" + id +
				", username='" + username + '\'' +
				", content='" + content + '\'' +
				", avatar='" + avatar + '\'' +
				", createAt=" + createAt +
				", agreeCount=" + agreeCount +
				", againstCount=" + againstCount +
				", type=" + type +
				'}';
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
}
