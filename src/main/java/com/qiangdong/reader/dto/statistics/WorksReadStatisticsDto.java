package com.qiangdong.reader.dto.statistics;

import java.util.List;
import java.util.Map;

/**
 * 作品阅读数据
 * */
public class WorksReadStatisticsDto {
	private Integer readUserCount;
	private Integer commentCount;
	private List<ReadUserSexWithCount> readUserSexWithCounts;
	private List<ReadUserAgeWithCount> readUserAgeWithCounts;

	public Integer getReadUserCount() {
		return readUserCount;
	}

	public void setReadUserCount(Integer readUserCount) {
		this.readUserCount = readUserCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public List<ReadUserSexWithCount> getReadUserSexWithCounts() {
		return readUserSexWithCounts;
	}

	public void setReadUserSexWithCounts(
		List<ReadUserSexWithCount> readUserSexWithCounts) {
		this.readUserSexWithCounts = readUserSexWithCounts;
	}

	public List<ReadUserAgeWithCount> getReadUserAgeWithCounts() {
		return readUserAgeWithCounts;
	}

	public void setReadUserAgeWithCounts(
		List<ReadUserAgeWithCount> readUserAgeWithCounts) {
		this.readUserAgeWithCounts = readUserAgeWithCounts;
	}
}
