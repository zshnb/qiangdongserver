package com.qiangdong.reader.dto.statistics;

import java.math.BigDecimal;

/**
 * 用户充值排名
 * */
public class UserCreditRankDto {
	private String username;
	private BigDecimal count;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getCount() {
		return count;
	}

	public void setCount(BigDecimal count) {
		this.count = count;
	}
}
