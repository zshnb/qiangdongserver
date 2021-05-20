package com.qiangdong.reader.comment;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.WorksCommentDto;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comment.ListNovelCommentRequest;
import com.qiangdong.reader.request.comment.PublishNovelCommentRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServicePublishNovelCommentTest extends BaseTest {
	@Autowired
	private CommentServiceImpl commentService;

	@Test
	public void publishNovelCommentSuccessful() {
		PublishNovelCommentRequest request = new PublishNovelCommentRequest();
		request.setNovelId(1L);
		request.setUserId(1L);
		request.setContent("content");
		commentService.publishNovelComment(request, new Novel());

		ListNovelCommentRequest listNovelCommentRequest = new ListNovelCommentRequest();
		listNovelCommentRequest.setNovelId(1L);
		PageResponse<WorksCommentDto> response = commentService.listNovelComment(listNovelCommentRequest, new Novel());
		Assert.assertEquals(2, response.getList().size());
		Assert.assertEquals("content", response.getList().get(1).getContent());
	}

	@Test
	public void publishNovelCommentFailedWhenNovelIdIsInvalid() {
		PublishNovelCommentRequest request = new PublishNovelCommentRequest();
		request.setNovelId(-1L);
		request.setUserId(1L);
		request.setContent("content");
		assertException(InvalidArgumentException.class, () -> commentService.publishNovelComment(request, new Novel()));
	}
}
