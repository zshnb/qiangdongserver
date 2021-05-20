package com.qiangdong.reader.dto.novel;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qiangdong.reader.enums.common.WorksChapterTypeEnum;
import com.qiangdong.reader.enums.common.WorksUpdateStatusEnum;
import java.time.LocalDateTime;

public class NovelAndLastUpdateChapterDto {
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	private String name;
	private Integer collectCount;
	private WorksUpdateStatusEnum updateStatus;
	private String cover;
	private String title;
	private LocalDateTime updateAt;
	private WorksChapterTypeEnum type;
	private String typeName;
	private String parentTypeName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCollectCount() {
		return collectCount;
	}

	public void setCollectCount(Integer collectCount) {
		this.collectCount = collectCount;
	}

	public WorksUpdateStatusEnum getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(WorksUpdateStatusEnum updateStatus) {
		this.updateStatus = updateStatus;
	}

	public WorksChapterTypeEnum getType() {
		return type;
	}

	public void setType(WorksChapterTypeEnum type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getParentTypeName() {
		return parentTypeName;
	}

	public void setParentTypeName(String parentTypeName) {
		this.parentTypeName = parentTypeName;
	}
}
