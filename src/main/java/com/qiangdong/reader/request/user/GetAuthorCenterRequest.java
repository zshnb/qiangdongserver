package com.qiangdong.reader.request.user;

import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class GetAuthorCenterRequest extends BaseRequest {
	private WorksTypeEnum type = WorksTypeEnum.NONE;

	public WorksTypeEnum getType() {
		return type;
	}

	public void setType(WorksTypeEnum type) {
		this.type = type;
	}
}
