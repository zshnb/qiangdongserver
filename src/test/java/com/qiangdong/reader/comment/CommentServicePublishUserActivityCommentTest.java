package com.qiangdong.reader.comment;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dao.UserActivityMapper;
import com.qiangdong.reader.dto.MessageDto;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.enums.message.MessageTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comment.PublishUserActivityCommentRequest;
import com.qiangdong.reader.request.message.ListMessageByTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import com.qiangdong.reader.serviceImpl.MessageServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServicePublishUserActivityCommentTest extends BaseTest {
	@Autowired
	private CommentServiceImpl commentService;

	@Autowired
	private UserActivityMapper userActivityMapper;

	@Autowired
	private MessageServiceImpl messageService;

	@Test
	public void publishUserActivityCommentSuccessful() {
		PublishUserActivityCommentRequest request = new PublishUserActivityCommentRequest();
		request.setUserActivityId(1L);
		request.setUserId(2L);
		request.setContent("content");
		Comment comment = commentService.publishUserActivityComment(request).getData();
		assertThat(comment.getId()).isNotZero();

		UserActivity userActivity = userActivityMapper.findById(1L);
		assertThat(userActivity.getActivityData().getCreateActivity().getCommentCount()).isEqualTo(4);

		ListMessageByTypeRequest listMessageByTypeRequest = new ListMessageByTypeRequest();
		listMessageByTypeRequest.setMessageType(MessageTypeEnum.COMMENT);
		listMessageByTypeRequest.setUserId(1L);
		PageResponse<MessageDto> response = messageService.listMessageByType(listMessageByTypeRequest);
		assertThat(response.getList().size()).isEqualTo(8);
	}

	@Test
	public void publishUserActivityCommentFailedWhenNovelIdIsInvalid() {
		PublishUserActivityCommentRequest request = new PublishUserActivityCommentRequest();
		request.setUserActivityId(-1L);
		request.setUserId(1L);
		assertException(InvalidArgumentException.class, () -> {
			commentService.publishUserActivityComment(request);
		});
	}
}
