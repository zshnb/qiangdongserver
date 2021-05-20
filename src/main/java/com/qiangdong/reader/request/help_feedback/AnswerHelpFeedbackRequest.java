package com.qiangdong.reader.request.help_feedback;

import com.qiangdong.reader.request.BaseRequest;

public class AnswerHelpFeedbackRequest extends BaseRequest {
	private Long helpFeedbackId = 0L;
	private String answer = "";

	public Long getHelpFeedbackId() {
		return helpFeedbackId;
	}

	public void setHelpFeedbackId(Long helpFeedbackId) {
		this.helpFeedbackId = helpFeedbackId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
