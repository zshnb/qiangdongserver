package com.qiangdong.reader.request.draft;

import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class ListWorksDraftRequest extends BaseRequest {
	private Long worksId = 0L;
	private WorksTypeEnum worksType = WorksTypeEnum.NONE;

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
}
