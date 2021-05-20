package com.qiangdong.reader.request.user;

import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.enums.user_consumption.ConsumptionTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class ListWorksIncomeUserRankingRequest extends BaseRequest {
	private Long worksId = 0L;
	private WorksTypeEnum worksType = WorksTypeEnum.NONE;
	private ConsumptionTypeEnum consumptionType = ConsumptionTypeEnum.NONE;

	public Long getWorksId() {
		return worksId;
	}

	public void setWorksId(Long worksId) {
		this.worksId = worksId;
	}

	public WorksTypeEnum getWorksType() {
		return worksType;
	}

	public void setWorksType(WorksTypeEnum worksType) {
		this.worksType = worksType;
	}

	public ConsumptionTypeEnum getConsumptionType() {
		return consumptionType;
	}

	public void setConsumptionType(ConsumptionTypeEnum consumptionType) {
		this.consumptionType = consumptionType;
	}
}
