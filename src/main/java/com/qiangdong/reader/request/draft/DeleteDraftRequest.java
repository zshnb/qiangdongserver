package com.qiangdong.reader.request.draft;

import com.qiangdong.reader.request.BaseRequest;

public class DeleteDraftRequest extends BaseRequest {
	private Long draftId = 0L;

	public Long getDraftId() {
		return draftId;
	}

	public void setDraftId(Long draftId) {
		this.draftId = draftId;
	}
}
