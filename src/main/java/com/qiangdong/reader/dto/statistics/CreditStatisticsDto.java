package com.qiangdong.reader.dto.statistics;

import java.math.BigDecimal;

public class CreditStatisticsDto {
	private BigDecimal count;
	private String time;

	public BigDecimal getCount() {
		return count;
	}

	public void setCount(BigDecimal count) {
		this.count = count;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
