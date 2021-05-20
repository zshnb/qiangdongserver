package com.qiangdong.reader.request.bookstand;

import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class AddBookStandRequest extends BaseRequest {
	private Long associateId = 0L;
	private WorksTypeEnum associateType = WorksTypeEnum.NONE;

	public Long getAssociateId() {
		return associateId;
	}

	public void setAssociateId(Long associateId) {
		this.associateId = associateId;
	}

	public WorksTypeEnum getAssociateType() {
		return associateType;
	}

	public void setAssociateType(WorksTypeEnum associateType) {
		this.associateType = associateType;
	}
}
