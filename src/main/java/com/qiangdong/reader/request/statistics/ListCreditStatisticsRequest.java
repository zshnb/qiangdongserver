package com.qiangdong.reader.request.statistics;

import com.qiangdong.reader.enums.statistics.DateUnitEnum;
import com.qiangdong.reader.request.BaseRequest;

public class ListCreditStatisticsRequest extends BaseRequest {
	private DateUnitEnum dateUnit = DateUnitEnum.NONE;

	public DateUnitEnum getDateUnit() {
		return dateUnit;
	}

	public void setDateUnit(DateUnitEnum dateUnit) {
		this.dateUnit = dateUnit;
	}
}
