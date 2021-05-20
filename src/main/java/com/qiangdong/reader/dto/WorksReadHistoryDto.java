package com.qiangdong.reader.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;

public class WorksReadHistoryDto {
	@JsonSerialize(using = ToStringSerializer.class)
	private Long historyId;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long worksId;
	private String worksName;
	private String authorName;
	private String typeName;
	private String cover;
	private String avatar;
	private Integer lastReadChapterIndex;
	private LocalDateTime updateAt;

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

	public Long getWorksId() {
		return worksId;
	}

	public void setWorksId(Long worksId) {
		this.worksId = worksId;
	}

	public String getWorksName() {
		return worksName;
	}

	public void setWorksName(String worksName) {
		this.worksName = worksName;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getLastReadChapterIndex() {
		return lastReadChapterIndex;
	}

	public void setLastReadChapterIndex(Integer lastReadChapterIndex) {
		this.lastReadChapterIndex = lastReadChapterIndex;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}
}
