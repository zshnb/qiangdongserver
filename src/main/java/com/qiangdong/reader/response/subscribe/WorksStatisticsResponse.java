package com.qiangdong.reader.response.subscribe;


public class WorksStatisticsResponse {
	private Long id;
	private Integer collectCount;
	private Integer recommendTicket;
	private Integer wallTicket;
	private Integer subscribeCount;
	private Integer yesterdaySubscribeCount;
	private Integer averageChapterSubscribeCount;
	private Integer maxChapterSubscribeCount;
	private Integer clickCount;
	private Integer coin;
	private Integer commentCount;

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

	public Integer getSubscribeCount() {
		return subscribeCount;
	}

	public void setSubscribeCount(Integer subscribeCount) {
		this.subscribeCount = subscribeCount;
	}

	public Integer getYesterdaySubscribeCount() {
		return yesterdaySubscribeCount;
	}

	public void setYesterdaySubscribeCount(Integer yesterdaySubscribeCount) {
		this.yesterdaySubscribeCount = yesterdaySubscribeCount;
	}

	public Integer getAverageChapterSubscribeCount() {
		return averageChapterSubscribeCount;
	}

	public void setAverageChapterSubscribeCount(Integer averageChapterSubscribeCount) {
		this.averageChapterSubscribeCount = averageChapterSubscribeCount;
	}

	public Integer getMaxChapterSubscribeCount() {
		return maxChapterSubscribeCount;
	}

	public void setMaxChapterSubscribeCount(Integer maxChapterSubscribeCount) {
		this.maxChapterSubscribeCount = maxChapterSubscribeCount;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
}
