package com.qiangdong.reader.request.novel;

import com.qiangdong.reader.request.BaseRequest;

public class DeleteNovelChapterRequest extends BaseRequest {
	private Long novelId = 0L;

	private Long chapterId = 0L;

	public Long getNovelId() {
		return novelId;
	}

	public void setNovelId(Long novelId) {
		this.novelId = novelId;
	}

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}
}
