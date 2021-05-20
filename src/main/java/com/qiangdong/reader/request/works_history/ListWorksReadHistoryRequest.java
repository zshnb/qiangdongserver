package com.qiangdong.reader.request.works_history;

import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class ListWorksReadHistoryRequest extends BaseRequest {
	private WorksTypeEnum worksType = WorksTypeEnum.NONE;

	public WorksTypeEnum getWorksType() {
		return worksType;
	}

	public void setWorksType(WorksTypeEnum worksType) {
		this.worksType = worksType;
	}
}
