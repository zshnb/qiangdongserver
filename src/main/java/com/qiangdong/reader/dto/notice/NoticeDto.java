package com.qiangdong.reader.dto.notice;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import com.qiangdong.reader.enums.notice.NoticeTypeEnum;
import java.time.LocalDateTime;

public class NoticeDto {

	private Long id;

	private String title;

	private String subtitle;

	private String content;

	private String cover;

	private NoticeTypeEnum type = NoticeTypeEnum.NONE;

	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createAt = LocalDateTime.now();

	public NoticeTypeEnum getType() {
		return type;
	}

	public void setType(NoticeTypeEnum type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	@Override
	public String toString() {
		return "NoticeDto{" +
			"id=" + id +
			", title='" + title + '\'' +
			", subTitle='" + subtitle + '\'' +
			", content='" + content + '\'' +
			", cover='" + cover + '\'' +
			", type=" + type +
			", createAt=" + createAt +
			'}';
	}
}
