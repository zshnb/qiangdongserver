package com.qiangdong.reader.request.works_topic;


import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class ListWorksTopicRequest extends BaseRequest {
	private Long typeId = 0L;
	private WorksTypeEnum worksType = WorksTypeEnum.NONE;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public WorksTypeEnum getWorksType() {
		return worksType;
	}

	public void setWorksType(WorksTypeEnum worksType) {
		this.worksType = worksType;
	}
}
