package com.qiangdong.reader.dto.novel;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.qiangdong.reader.enums.common.WorksChapterTypeEnum;
import java.time.LocalDateTime;

public class NovelChapterDto {
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id = 0L;
	private Integer index = 0;
	private String title = "";
	private Double price = 0.0;
	private WorksChapterTypeEnum type = WorksChapterTypeEnum.NONE;
	private String txtUrl = "";
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime updateAt;
	private Integer wordCount = 0;

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public Integer getWordCount() {
		return wordCount;
	}

	public void setWordCount(Integer wordCount) {
		this.wordCount = wordCount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public WorksChapterTypeEnum getType() {
		return type;
	}

	public void setType(WorksChapterTypeEnum type) {
		this.type = type;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getTxtUrl() {
		return txtUrl;
	}

	public void setTxtUrl(String txtUrl) {
		this.txtUrl = txtUrl;
	}
}
