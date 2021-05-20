package com.qiangdong.reader.request.comment;

import com.qiangdong.reader.enums.comment.CommentTypeEnum;
import com.qiangdong.reader.request.BaseRequest;
import javax.validation.constraints.Size;

public class ReplyCommentRequest extends BaseRequest {
	private Long commentId = 0L;
	@Size(min = 1, max = 200, message = "评论在1-200字之间")
	private String content = "";
	private CommentTypeEnum type = CommentTypeEnum.REPLY;

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public CommentTypeEnum getType() {
		return type;
	}

	public void setType(CommentTypeEnum type) {
		this.type = type;
	}
}
