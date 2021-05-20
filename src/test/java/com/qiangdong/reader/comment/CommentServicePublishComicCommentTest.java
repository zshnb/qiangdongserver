package com.qiangdong.reader.comment;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.WorksCommentDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comment.ListComicCommentRequest;
import com.qiangdong.reader.request.comment.PublishComicCommentRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServicePublishComicCommentTest extends BaseTest {
	@Autowired
	private CommentServiceImpl commentService;

	@Test
	public void publishComicCommentSuccessful() {
		PublishComicCommentRequest request = new PublishComicCommentRequest();
		request.setComicId(1L);
		request.setUserId(1L);
		request.setContent("content");
		commentService.publishComicComment(request, new Comic());

		ListComicCommentRequest listComicCommentRequest = new ListComicCommentRequest();
		listComicCommentRequest.setComicId(1L);
		PageResponse<WorksCommentDto> response = commentService.listComicComment(listComicCommentRequest, new Comic());
		assertThat(response.getList().size()).isEqualTo(4);
	}

	@Test
	public void publishNovelCommentFailedWhenNovelIdIsInvalid() {
		PublishComicCommentRequest request = new PublishComicCommentRequest();
		request.setComicId(-1L);
		request.setUserId(1L);
		request.setContent("content");
		assertException(InvalidArgumentException.class, () -> {
			commentService.publishComicComment(request, new Comic());
		});
	}
}
