package com.qiangdong.reader.request.works_history;

import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class AddOrUpdateWorksHistoryRequest extends BaseRequest {
	private Long historyId = 0L;
	private Long worksId = 0L;
	private Integer lastReadChapterIndex = 0;
	private WorksTypeEnum worksType = WorksTypeEnum.NONE;

	public Long getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Long historyId) {
		this.historyId = historyId;
	}

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

	public Integer getLastReadChapterIndex() {
		return lastReadChapterIndex;
	}

	public void setLastReadChapterIndex(Integer lastReadChapterIndex) {
		this.lastReadChapterIndex = lastReadChapterIndex;
	}
}
