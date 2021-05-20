package com.qiangdong.reader.request.comment;

import com.qiangdong.reader.request.BaseRequest;
import javax.validation.constraints.Size;

public class PublishNovelCommentRequest extends BaseRequest {
	private Long novelId = 0L;

	@Size(min = 1, max = 200, message = "评论在1-200字之间")
	private String content = "";

	public Long getNovelId() {
		return novelId;
	}

	public void setNovelId(Long novelId) {
		this.novelId = novelId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
