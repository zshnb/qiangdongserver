package com.qiangdong.reader.request.comment;

import com.qiangdong.reader.request.BaseRequest;

public class AgreeCommentRequest extends BaseRequest {
	private Long commentId = 0L;

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
}
