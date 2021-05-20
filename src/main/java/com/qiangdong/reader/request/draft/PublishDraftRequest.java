package com.qiangdong.reader.request.draft;

import com.qiangdong.reader.enums.common.WorksChapterTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class PublishDraftRequest extends BaseRequest {
	private Long draftId = 0L;
	private WorksChapterTypeEnum worksChapterType = WorksChapterTypeEnum.NONE;

	public Long getDraftId() {
		return draftId;
	}

	public void setDraftId(Long draftId) {
		this.draftId = draftId;
	}

	public WorksChapterTypeEnum getWorksChapterType() {
		return worksChapterType;
	}

	public void setWorksChapterType(WorksChapterTypeEnum worksChapterType) {
		this.worksChapterType = worksChapterType;
	}
}
