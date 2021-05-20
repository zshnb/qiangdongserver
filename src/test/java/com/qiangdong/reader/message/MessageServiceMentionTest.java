package com.qiangdong.reader.message;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Message;
import com.qiangdong.reader.enums.message.MessageTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comment.MentionRequest;
import com.qiangdong.reader.serviceImpl.MessageServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageServiceMentionTest extends BaseTest {
	@Autowired
	private MessageServiceImpl messageService;

	@Test
	public void commentMentionSuccessful() {
		MentionRequest request = new MentionRequest();
		request.setTargetUserId(1L);
		request.setAssociateId(1L);
		request.setType(MessageTypeEnum.COMMENT_MENTION);
		messageService.mention(request);

		Message message = messageService.getOne(new QueryWrapper<Message>()
			.eq("user_id", 1L)
			.eq("type", MessageTypeEnum.COMMENT_MENTION));
		assertThat(message.getId()).isNotZero();
	}

	@Test
	public void userActivityMentionSuccessful() {
		MentionRequest request = new MentionRequest();
		request.setTargetUserId(1L);
		request.setAssociateId(1L);
		request.setType(MessageTypeEnum.USER_ACTIVITY_MENTION);
		messageService.mention(request);

		Message message = messageService.getOne(new QueryWrapper<Message>()
			.eq("user_id", 1L)
			.eq("type", MessageTypeEnum.USER_ACTIVITY_MENTION));
		assertThat(message.getId()).isNotZero();
	}

	@Test
	public void commentMentionFailedWhenCommentNotExist() {
		MentionRequest request = new MentionRequest();
		request.setTargetUserId(1L);
		request.setAssociateId(-1L);
		request.setType(MessageTypeEnum.COMMENT_MENTION);
		assertException(InvalidArgumentException.class, () -> messageService.mention(request));
	}

	@Test
	public void userActivityMentionFailedWhenCommentNotExist() {
		MentionRequest request = new MentionRequest();
		request.setTargetUserId(1L);
		request.setAssociateId(-1L);
		request.setType(MessageTypeEnum.USER_ACTIVITY_MENTION);
		assertException(InvalidArgumentException.class, () -> messageService.mention(request));
	}

	@Test
	public void mentionFailedWhenUserNotExist() {
		MentionRequest request = new MentionRequest();
		request.setTargetUserId(-1L);
		request.setAssociateId(1L);
		request.setType(MessageTypeEnum.USER_ACTIVITY_MENTION);
		assertException(InvalidArgumentException.class, () -> messageService.mention(request));
	}
}
