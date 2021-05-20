package com.qiangdong.reader.request.comic;

import com.qiangdong.reader.request.BaseRequest;

public class GetComicChapterRequest extends BaseRequest {
	private Long comicId = 0L;

	private Long chapterId = 0L;

	private Integer index = 0;

	public Long getComicId() {
		return comicId;
	}

	public void setComicId(Long comicId) {
		this.comicId = comicId;
	}

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
