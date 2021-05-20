package com.qiangdong.reader.request.subscribe;

import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class SubscribeChapterRequest extends BaseRequest {
	private Long worksId = 0L;
	private Long chapterId = 0L;
	private WorksTypeEnum worksType = WorksTypeEnum.NONE;

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public WorksTypeEnum getWorksType() {
		return worksType;
	}

	public void setWorksType(WorksTypeEnum worksType) {
		this.worksType = worksType;
	}

	public Long getWorksId() {
		return worksId;
	}

	public void setWorksId(Long worksId) {
		this.worksId = worksId;
	}
}
