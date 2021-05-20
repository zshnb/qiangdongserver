package com.qiangdong.reader.comment;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comment.PublishComicChapterCommentRequest;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServicePublishComicChapterCommentTest extends BaseTest {
	@Autowired
	private CommentServiceImpl commentService;

	@Test
	public void publishComicChapterCommentSuccessful() {
		PublishComicChapterCommentRequest request = new PublishComicChapterCommentRequest();
		request.setChapterId(1L);
		request.setUserId(1L);
		request.setContent("content");
		commentService.publishComicChapterComment(request);

		// TODO list chapter comments
	}

	@Test
	public void publishComicChapterCommentFailedWhenChapterIdIsInvalid() {
		PublishComicChapterCommentRequest request = new PublishComicChapterCommentRequest();
		request.setChapterId(-1L);
		request.setUserId(1L);
		request.setContent("content");
		assertException(InvalidArgumentException.class, () -> {
			commentService.publishComicChapterComment(request);
		});
	}
}
