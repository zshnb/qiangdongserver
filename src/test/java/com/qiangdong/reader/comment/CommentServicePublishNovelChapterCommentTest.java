package com.qiangdong.reader.comment;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comment.PublishNovelChapterCommentRequest;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServicePublishNovelChapterCommentTest extends BaseTest {
	@Autowired
	private CommentServiceImpl commentService;

	@Test
	public void publishNovelChapterCommentSuccessful() {
		PublishNovelChapterCommentRequest request = new PublishNovelChapterCommentRequest();
		request.setChapterId(1L);
		request.setUserId(1L);
		request.setContent("content");
		commentService.publishNovelChapterComment(request);
	}

	@Test
	public void publishNovelCommentFailedWhenChapterIdIsInvalid() {
		PublishNovelChapterCommentRequest request = new PublishNovelChapterCommentRequest();
		request.setChapterId(-1L);
		request.setUserId(1L);
		request.setContent("content");
		assertException(InvalidArgumentException.class, () -> commentService.publishNovelChapterComment(request));
	}
}
