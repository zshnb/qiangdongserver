package com.qiangdong.reader.comment;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.comment.CommentDto;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comment.ListUserActivityCommentRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServiceListUserActivityCommentTest extends BaseTest {
	@Autowired
	private CommentServiceImpl commentService;

	@Test
	public void listUserActivityCommentSuccessful() {
		ListUserActivityCommentRequest request = new ListUserActivityCommentRequest();
		request.setUserActivityId(1L);
		PageResponse<CommentDto> response =
			commentService.listUserActivityComment(request, new UserActivity());
		Assert.assertEquals(1, response.getList().size());
		Assert.assertEquals("user1", response.getList().get(0).getUsername());
		Assert.assertEquals("content", response.getList().get(0).getContent());
	}

	@Test
	public void listUserActivityFailedWhenActivityNotExist() {
		ListUserActivityCommentRequest request = new ListUserActivityCommentRequest();
		request.setUserActivityId(-1L);
		assertException(InvalidArgumentException.class, () ->{
			commentService.listUserActivityComment(request, new UserActivity());
		});
	}
}
