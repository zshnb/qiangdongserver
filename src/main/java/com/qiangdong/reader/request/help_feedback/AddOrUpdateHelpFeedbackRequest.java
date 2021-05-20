package com.qiangdong.reader.request.help_feedback;

import com.qiangdong.reader.request.BaseRequest;

public class AddOrUpdateHelpFeedbackRequest extends BaseRequest {
	private Long helpFeedbackId = 0L;
	private String type = "";
	private String content = "";
	private String images = "";

	public Long getHelpFeedbackId() {
		return helpFeedbackId;
	}

	public void setHelpFeedbackId(Long helpFeedbackId) {
		this.helpFeedbackId = helpFeedbackId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
}
