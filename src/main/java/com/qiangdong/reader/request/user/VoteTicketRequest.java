package com.qiangdong.reader.request.user;

import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class VoteTicketRequest extends BaseRequest {
	private Long worksId = 0L;
	private Integer count = 0;
	private WorksTypeEnum worksType = WorksTypeEnum.NONE;

	public Long getWorksId() {
		return worksId;
	}

	public void setWorksId(Long worksId) {
		this.worksId = worksId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public WorksTypeEnum getWorksType() {
		return worksType;
	}

	public void setWorksType(WorksTypeEnum worksType) {
		this.worksType = worksType;
	}
}
