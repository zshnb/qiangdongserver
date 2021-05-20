package com.qiangdong.reader.comment;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.comment.CommentDto;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comment.ListReplyRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServiceListReplyTest extends BaseTest {
	@Autowired
	private CommentServiceImpl commentService;

	@Test
	public void listUserActivityCommentSuccessful() {
		ListReplyRequest request = new ListReplyRequest();
		request.setCommentId(9L);
		PageResponse<CommentDto> response = commentService.listReply(request, new Comment());
		Assert.assertEquals(1, response.getList().size());
		Assert.assertEquals("user1", response.getList().get(0).getUsername());
		Assert.assertEquals("content", response.getList().get(0).getContent());
	}

	@Test
	public void listUserActivityFailedWhenActivityNotExist() {
		ListReplyRequest request = new ListReplyRequest();
		request.setCommentId(-1L);
		assertException(InvalidArgumentException.class, () ->{
			commentService.listReply(request, new Comment());
		});
	}
}
