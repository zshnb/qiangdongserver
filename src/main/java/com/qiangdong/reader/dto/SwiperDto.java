package com.qiangdong.reader.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qiangdong.reader.config.JsonLongSerializer;
import com.qiangdong.reader.enums.swiper.LinkTypeEnum;
import java.time.LocalDateTime;

public class SwiperDto {
	@JsonSerialize(using = JsonLongSerializer.class)
	private Long id;
	private String cover;
	private LinkTypeEnum linkType;
	private String link;
	private String typeName;
	private Long worksId;
	private String worksName;
	private Integer index;
	private LocalDateTime expireAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public LinkTypeEnum getLinkType() {
		return linkType;
	}

	public void setLinkType(LinkTypeEnum linkType) {
		this.linkType = linkType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public LocalDateTime getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(LocalDateTime expireAt) {
		this.expireAt = expireAt;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
