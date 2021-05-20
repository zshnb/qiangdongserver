package com.qiangdong.reader.request.bookstand;

import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class ListBookStandByTypeRequest extends BaseRequest {
	private WorksTypeEnum type = WorksTypeEnum.NONE;

	public WorksTypeEnum getType() {
		return type;
	}

	public void setType(WorksTypeEnum type) {
		this.type = type;
	}
}
