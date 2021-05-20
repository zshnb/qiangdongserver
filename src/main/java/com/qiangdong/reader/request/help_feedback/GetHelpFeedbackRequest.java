package com.qiangdong.reader.request.help_feedback;

import com.qiangdong.reader.request.BaseRequest;

public class GetHelpFeedbackRequest extends BaseRequest {

	private Long helpFeedbackId = 0L;

	public Long getHelpFeedbackId() {
		return helpFeedbackId;
	}

	public void setHelpFeedbackId(Long helpFeedbackId) {
		this.helpFeedbackId = helpFeedbackId;
	}
}
