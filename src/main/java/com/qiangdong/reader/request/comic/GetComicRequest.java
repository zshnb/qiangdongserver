package com.qiangdong.reader.request.comic;

import com.qiangdong.reader.request.BaseRequest;

public class GetComicRequest extends BaseRequest {
	private Long comicId = 0L;

	public Long getComicId() {
		return comicId;
	}

	public void setComicId(Long comicId) {
		this.comicId = comicId;
	}
}
