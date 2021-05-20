package com.qiangdong.reader.dto.statistics;

import java.math.BigDecimal;

/**
 * 数据总栏数据
 */
public class StatisticsDataOverviewDto {
	private Integer registerCount; // 累计注册用户
	private Double registerIncrementRatio; // 较昨日注册增加率
	private Integer dauCount; // 日活跃用户
	private Double dauCountIncrementRatio; // 较昨日日活跃增加率
	private BigDecimal creditCount; // 累计充值数
	private Double creditIncrementRatio; // 较昨日充值增加率
	private Integer creditUserCount; // 累计充值用户
	private Double creditUserIncrementRatio; // 较昨日充值用户增加率

	public Integer getRegisterCount() {
		return registerCount;
	}

	public void setRegisterCount(Integer registerCount) {
		this.registerCount = registerCount;
	}

	public Double getRegisterIncrementRatio() {
		return registerIncrementRatio;
	}

	public void setRegisterIncrementRatio(Double registerIncrementRatio) {
		this.registerIncrementRatio = registerIncrementRatio;
	}

	public Integer getDauCount() {
		return dauCount;
	}

	public void setDauCount(Integer dauCount) {
		this.dauCount = dauCount;
	}

	public Double getDauCountIncrementRatio() {
		return dauCountIncrementRatio;
	}

	public void setDauCountIncrementRatio(Double dauCountIncrementRatio) {
		this.dauCountIncrementRatio = dauCountIncrementRatio;
	}

	public BigDecimal getCreditCount() {
		return creditCount;
	}

	public void setCreditCount(BigDecimal creditCount) {
		this.creditCount = creditCount;
	}

	public Double getCreditIncrementRatio() {
		return creditIncrementRatio;
	}

	public void setCreditIncrementRatio(Double creditIncrementRatio) {
		this.creditIncrementRatio = creditIncrementRatio;
	}

	public Integer getCreditUserCount() {
		return creditUserCount;
	}

	public void setCreditUserCount(Integer creditUserCount) {
		this.creditUserCount = creditUserCount;
	}

	public Double getCreditUserIncrementRatio() {
		return creditUserIncrementRatio;
	}

	public void setCreditUserIncrementRatio(Double creditUserIncrementRatio) {
		this.creditUserIncrementRatio = creditUserIncrementRatio;
	}
}
