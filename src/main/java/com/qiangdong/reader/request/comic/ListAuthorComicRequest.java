package com.qiangdong.reader.request.comic;

import com.qiangdong.reader.request.BaseRequest;

public class ListAuthorComicRequest extends BaseRequest {
	private Long authorId = 0L;

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
}
