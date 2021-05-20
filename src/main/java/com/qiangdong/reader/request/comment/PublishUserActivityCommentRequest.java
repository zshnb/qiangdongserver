package com.qiangdong.reader.request.comment;

import com.qiangdong.reader.request.BaseRequest;
import javax.validation.constraints.Size;

public class PublishUserActivityCommentRequest extends BaseRequest {
	private Long userActivityId = 0L;

	@Size(min = 1, max = 200, message = "评论在1-200字之间")
	private String content = "";

	private String images = "";

	public Long getUserActivityId() {
		return userActivityId;
	}

	public void setUserActivityId(Long userActivityId) {
		this.userActivityId = userActivityId;
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
