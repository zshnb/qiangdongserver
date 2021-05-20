package com.qiangdong.reader.request.bookstand;

import com.qiangdong.reader.request.BaseRequest;

public class DeleteBookStandRequest extends BaseRequest {
	private Long bookStandId = 0L;

	public Long getBookStandId() {
		return bookStandId;
	}

	public void setBookStandId(Long bookStandId) {
		this.bookStandId = bookStandId;
	}
}
