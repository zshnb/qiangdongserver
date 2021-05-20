package com.qiangdong.reader.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class LastUpdateWorksSummaryDto {
	@JsonSerialize(using = ToStringSerializer.class)
	private Long worksId = 0L;
	private String name = "";
	private Integer recommendTicket = 0;
	private Integer wallTicket = 0;
	private Integer coin = 0;
	private Integer clickCount = 0;
	private Integer commentCount = 0;
	private Integer collectCount = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRecommendTicket() {
		return recommendTicket;
	}

	public void setRecommendTicket(Integer recommendTicket) {
		this.recommendTicket = recommendTicket;
	}

	public Integer getWallTicket() {
		return wallTicket;
	}

	public void setWallTicket(Integer wallTicket) {
		this.wallTicket = wallTicket;
	}

	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getCollectCount() {
		return collectCount;
	}

	public void setCollectCount(Integer collectCount) {
		this.collectCount = collectCount;
	}

	public Long getWorksId() {
		return worksId;
	}

	public void setWorksId(Long worksId) {
		this.worksId = worksId;
	}
}
