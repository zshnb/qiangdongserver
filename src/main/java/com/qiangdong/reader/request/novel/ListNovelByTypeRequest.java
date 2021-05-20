package com.qiangdong.reader.request.novel;

import com.qiangdong.reader.enums.common.WorksUpdateStatusEnum;
import com.qiangdong.reader.request.BaseRequest;

public class ListNovelByTypeRequest extends BaseRequest {
	private Long typeId = 0L;
	private Filter filter = new Filter();

	public static class Filter {
		Integer minWordCount = 0;
		Integer maxWordCount = Integer.MAX_VALUE;
		WorksUpdateStatusEnum updateStatus = WorksUpdateStatusEnum.NONE;

		public Integer getMinWordCount() {
			return minWordCount;
		}

		public void setMinWordCount(Integer minWordCount) {
			this.minWordCount = minWordCount;
		}

		public Integer getMaxWordCount() {
			return maxWordCount;
		}

		public void setMaxWordCount(Integer maxWordCount) {
			this.maxWordCount = maxWordCount;
		}

		public WorksUpdateStatusEnum getUpdateStatus() {
			return updateStatus;
		}

		public void setUpdateStatus(WorksUpdateStatusEnum updateStatus) {
			this.updateStatus = updateStatus;
		}
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
}
