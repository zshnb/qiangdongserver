package com.qiangdong.reader.request.notice;

import com.qiangdong.reader.request.BaseRequest;

public class GetNoticeRequest extends BaseRequest {

	private Long noticeId = 0L;

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}
}
