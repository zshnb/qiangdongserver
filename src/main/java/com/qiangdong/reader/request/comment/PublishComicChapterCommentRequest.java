package com.qiangdong.reader.request.comment;

import com.qiangdong.reader.request.BaseRequest;
import javax.validation.constraints.Size;

public class PublishComicChapterCommentRequest extends BaseRequest {
	private Long chapterId = 0L;

	@Size(min = 1, max = 200, message = "评论在1-200字之间")
	private String content = "";

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getChapterId() {
		return chapterId;
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}
}
