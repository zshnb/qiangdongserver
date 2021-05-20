package com.qiangdong.reader.message;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.FollowRelationDto;
import com.qiangdong.reader.dto.MessageDto;
import com.qiangdong.reader.dto.comment.CommentDto;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.entity.Message;
import com.qiangdong.reader.entity.Notice;
import com.qiangdong.reader.enums.comment.CommentTypeEnum;
import com.qiangdong.reader.enums.message.MessageReadStatusEnum;
import com.qiangdong.reader.enums.message.MessageTypeEnum;
import com.qiangdong.reader.enums.user_activity.AgreeActivityTypeEnum;
import com.qiangdong.reader.request.comment.MentionRequest;
import com.qiangdong.reader.request.comment.PublishUserActivityCommentRequest;
import com.qiangdong.reader.request.follow_relation.FollowUserRequest;
import com.qiangdong.reader.request.message.ListMessageByTypeRequest;
import com.qiangdong.reader.request.user_activity.AgreeActivityRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import com.qiangdong.reader.serviceImpl.FollowRelationServiceImpl;
import com.qiangdong.reader.serviceImpl.MessageServiceImpl;
import com.qiangdong.reader.serviceImpl.NoticeServiceImpl;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageServiceListMessageByTypeTest extends BaseTest {
	@Autowired
	private MessageServiceImpl messageService;

	@Autowired
	private CommentServiceImpl commentService;

	@Autowired
	private UserActivityServiceImpl userActivityService;

	@Autowired
	private FollowRelationServiceImpl followRelationService;

	@Autowired
	private NoticeServiceImpl noticeService;

	@Test
	public void listAgreeActivityMessage() {
		AgreeActivityRequest agreeActivityRequest = new AgreeActivityRequest();
		agreeActivityRequest.setUserId(2L);
		agreeActivityRequest.setActivityId(1L);
		userActivityService.agreeActivity(agreeActivityRequest);

		ListMessageByTypeRequest request = new ListMessageByTypeRequest();
		request.setUserId(1L);
		request.setMessageType(MessageTypeEnum.AGREE);
		PageResponse<MessageDto> response = messageService.listMessageByType(request);
		assertThat(response.getList().size()).isEqualTo(1);
		MessageDto messageDto = response.getList().get(0);
		assertThat(messageDto.getImage()).isEqualTo("image");
		UserActivityDto activityDto = (UserActivityDto) messageDto.getMessage();
		assertThat(activityDto.getActivityData().getAgreeActivity().getActivityId()).isEqualTo(1L);
		assertThat(activityDto.getActivityData().getAgreeActivity().getType()).isEqualTo(AgreeActivityTypeEnum.USER_ACTIVITY);
	}

	@Test
	public void listFollowMessage() {
		FollowUserRequest followUserRequest = new FollowUserRequest();
		followUserRequest.setUserId(1L);
		followUserRequest.setTargetUserId(3L);
		followRelationService.followUser(followUserRequest);

		ListMessageByTypeRequest request = new ListMessageByTypeRequest();
		request.setUserId(3L);
		request.setMessageType(MessageTypeEnum.FOLLOW);
		PageResponse<MessageDto> response = messageService.listMessageByType(request);
		assertThat(response.getList().size()).isEqualTo(1);
		MessageDto messageDto = response.getList().get(0);
		FollowRelationDto followRelationDto = (FollowRelationDto) messageDto.getMessage();
		assertThat(followRelationDto.getUsername()).isEqualTo("user1");
	}

	@Test
	public void listSystemMessage() {
		Notice responseNotice = noticeService.addOrUpdateNotice(addOrUpdateNoticeRequest).getData();

		ListMessageByTypeRequest request = new ListMessageByTypeRequest();
		request.setUserId(3L);
		request.setMessageType(MessageTypeEnum.SYSTEM_MESSAGE);
		PageResponse<MessageDto> response = messageService.listMessageByType(request);
		assertThat(response.getList().size()).isEqualTo(2);
		MessageDto messageDto = response.getList().get(0);
		Notice notice = (Notice) messageDto.getMessage();
		assertThat(notice.getId()).isEqualTo(responseNotice.getId());
		assertThat(notice.getTitle()).isEqualTo("test Title");
	}

	@Test
	public void listCommentMentionMessage() {
		PublishUserActivityCommentRequest publishUserActivityCommentRequest = new PublishUserActivityCommentRequest();
		publishUserActivityCommentRequest.setUserActivityId(1L);
		publishUserActivityCommentRequest.setUserId(2L);
		Comment comment = commentService.publishUserActivityComment(publishUserActivityCommentRequest).getData();
		MentionRequest request = new MentionRequest();
		request.setUserId(2L);
		request.setTargetUserId(1L);
		request.setAssociateId(comment.getId());
		request.setType(MessageTypeEnum.COMMENT_MENTION);
		messageService.mention(request);

		ListMessageByTypeRequest listMessageByTypeRequest = new ListMessageByTypeRequest();
		listMessageByTypeRequest.setUserId(1L);
		listMessageByTypeRequest.setMessageType(MessageTypeEnum.COMMENT_MENTION);
		PageResponse<MessageDto> response = messageService.listMessageByType(listMessageByTypeRequest);
		assertThat(response.getList().size()).isEqualTo(1);
		MessageDto messageDto = response.getList().get(0);
		CommentDto commentDto = (CommentDto) messageDto.getMessage();
		assertThat(commentDto.getUsername()).isEqualTo("user2");
	}

	@Test
	public void listUserActivityMentionMessage() {
		MentionRequest request = new MentionRequest();
		request.setTargetUserId(1L);
		request.setAssociateId(1L);
		request.setType(MessageTypeEnum.USER_ACTIVITY_MENTION);
		messageService.mention(request);

		ListMessageByTypeRequest listMessageByTypeRequest = new ListMessageByTypeRequest();
		listMessageByTypeRequest.setUserId(1L);
		listMessageByTypeRequest.setMessageType(MessageTypeEnum.USER_ACTIVITY_MENTION);
		PageResponse<MessageDto> response = messageService.listMessageByType(listMessageByTypeRequest);
		assertThat(response.getList().size()).isEqualTo(1);
		MessageDto messageDto = response.getList().get(0);
		UserActivityDto userActivityDto = (UserActivityDto) messageDto.getMessage();
		assertThat(userActivityDto.getId()).isEqualTo(1L);
	}

	@Test
	public void listCommentMessage() {
		PublishUserActivityCommentRequest request = new PublishUserActivityCommentRequest();
		request.setUserId(userId);
		request.setUserActivityId(1L);
		request.setContent("content");
		commentService.publishUserActivityComment(request);

		ListMessageByTypeRequest listMessageByTypeRequest = new ListMessageByTypeRequest();
		listMessageByTypeRequest.setUserId(1L);
		listMessageByTypeRequest.setMessageType(MessageTypeEnum.COMMENT);
		PageResponse<MessageDto> response = messageService.listMessageByType(listMessageByTypeRequest);
		assertThat(response.getList().size()).isEqualTo(8);
	}
}
