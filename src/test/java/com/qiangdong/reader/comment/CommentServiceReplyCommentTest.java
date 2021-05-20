package com.qiangdong.reader.comment;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.comment.CommentDto;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comment.ListReplyRequest;
import com.qiangdong.reader.request.comment.ReplyCommentRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServiceReplyCommentTest extends BaseTest {
	@Autowired
	private CommentServiceImpl commentService;

	@Test
	public void replyCommentSuccessful() {
		ReplyCommentRequest request = new ReplyCommentRequest();
		request.setUserId(2L);
		request.setCommentId(1L);
		request.setContent("reply");
		Comment comment = commentService.replyComment(request).getData();
		assertThat(comment.getId()).isGreaterThan(0L);
	}

	@Test
	public void replyReplySuccessful() {
		ReplyCommentRequest request = new ReplyCommentRequest();
		request.setUserId(2L);
		request.setCommentId(9L);
		request.setContent("reply");
		Comment comment = commentService.replyComment(request).getData();

		request.setUserId(3L);
		request.setContent("reply reply");
		request.setCommentId(comment.getId());
		Comment reply = commentService.replyComment(request).getData();

		request.setUserId(1L);
		request.setContent("reply reply reply");
		request.setCommentId(reply.getId());
		commentService.replyComment(request);

		ListReplyRequest listReplyRequest = new ListReplyRequest();
		listReplyRequest.setCommentId(9L);
		PageResponse<CommentDto> response = commentService.listReply(listReplyRequest, new Comment());
		assertThat(response.getList().size()).isEqualTo(4);
	}

	@Test
	public void replyFailedWhenCommentNotExist() {
		ReplyCommentRequest request = new ReplyCommentRequest();
		request.setUserId(2L);
		request.setCommentId(-1L);
		request.setContent("reply");
		assertException(InvalidArgumentException.class, () -> {
			commentService.replyComment(request);
		});
	}
}
