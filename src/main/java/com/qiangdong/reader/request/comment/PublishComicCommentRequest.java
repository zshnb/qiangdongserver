package com.qiangdong.reader.request.comment;

import com.qiangdong.reader.request.BaseRequest;
import javax.validation.constraints.Size;

public class PublishComicCommentRequest extends BaseRequest {
	private Long comicId = 0L;

	@Size(min = 1, max = 200, message = "评论在1-200字之间")
	private String content = "";

	public Long getComicId() {
		return comicId;
	}

	public void setComicId(Long comicId) {
		this.comicId = comicId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
