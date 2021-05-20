package com.qiangdong.reader.request.swiper;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qiangdong.reader.annotation.NotNone;
import com.qiangdong.reader.enums.swiper.LinkTypeEnum;
import com.qiangdong.reader.enums.type.TypeBelongEnum;
import com.qiangdong.reader.request.BaseRequest;
import java.time.LocalDateTime;

public class AddOrUpdateSwiperRequest extends BaseRequest {
	private Long swiperId = 0L;

	private Long itemId = 0L;

	private Integer index = 0;

	private String cover = "";

	@NotNone(message = "作品类型不合法")
	private TypeBelongEnum type = TypeBelongEnum.NONE;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime expireAt = LocalDateTime.now();

	@NotNone(message = "跳转方式不合法")
	private LinkTypeEnum linkType = LinkTypeEnum.NONE;

	private String link = "";

	public Long getSwiperId() {
		return swiperId;
	}

	public void setSwiperId(Long swiperId) {
		this.swiperId = swiperId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public TypeBelongEnum getType() {
		return type;
	}

	public void setType(TypeBelongEnum type) {
		this.type = type;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public LocalDateTime getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(LocalDateTime expireAt) {
		this.expireAt = expireAt;
	}

	public LinkTypeEnum getLinkType() {
		return linkType;
	}

	public void setLinkType(LinkTypeEnum linkType) {
		this.linkType = linkType;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
