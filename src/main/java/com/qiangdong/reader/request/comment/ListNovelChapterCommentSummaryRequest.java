package com.qiangdong.reader.request.comment;

import com.qiangdong.reader.request.BaseRequest;

public class ListNovelChapterCommentSummaryRequest extends BaseRequest {
	private Long novelId = 0L;

	public Long getNovelId() {
		return novelId;
	}

	public void setNovelId(Long novelId) {
		this.novelId = novelId;
	}
}
