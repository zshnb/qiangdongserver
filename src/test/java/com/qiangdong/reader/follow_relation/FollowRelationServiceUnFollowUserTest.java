package com.qiangdong.reader.follow_relation;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.MessageDto;
import com.qiangdong.reader.entity.FollowRelation;
import com.qiangdong.reader.enums.message.MessageTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.follow_relation.FollowUserRequest;
import com.qiangdong.reader.request.follow_relation.UnFollowUserRequest;
import com.qiangdong.reader.request.message.ListMessageByTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.FollowRelationServiceImpl;
import com.qiangdong.reader.serviceImpl.MessageServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FollowRelationServiceUnFollowUserTest extends BaseTest {
	@Autowired
	private FollowRelationServiceImpl followRelationService;

	@Autowired
	private MessageServiceImpl messageService;

	@Test
	public void unFollowUserSuccessful() {
		UnFollowUserRequest request = new UnFollowUserRequest();
		request.setUserId(1L);
		request.setTargetUserId(2L);
		followRelationService.unFollowUser(request);

		FollowRelation entity = followRelationService.getById(2L);
		assertThat(entity.getFollowEach()).isNotTrue();
	}

	@Test
	public void followThenUnFollow() {
		FollowUserRequest followUserRequest = new FollowUserRequest();
		followUserRequest.setUserId(1L);
		followUserRequest.setTargetUserId(3L);
		followRelationService.followUser(followUserRequest);

		ListMessageByTypeRequest listMessageByTypeRequest = new ListMessageByTypeRequest();
		listMessageByTypeRequest.setUserId(3L);
		listMessageByTypeRequest.setMessageType(MessageTypeEnum.FOLLOW);
		PageResponse<MessageDto> response = messageService.listMessageByType(listMessageByTypeRequest);
		assertThat(response.getList().size()).isEqualTo(1);

		UnFollowUserRequest request = new UnFollowUserRequest();
		request.setUserId(1L);
		request.setTargetUserId(3L);
		followRelationService.unFollowUser(request);

		listMessageByTypeRequest.setUserId(3L);
		listMessageByTypeRequest.setMessageType(MessageTypeEnum.FOLLOW);
		response = messageService.listMessageByType(listMessageByTypeRequest);
		assertThat(response.getList().size()).isEqualTo(0);
	}

	@Test
	public void unFollowUserFailedWhenTargetUserNotExist() {
		UnFollowUserRequest request = new UnFollowUserRequest();
		request.setUserId(1L);
		request.setTargetUserId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			followRelationService.unFollowUser(request);
		});
	}

	@Test
	public void unFollowUserFailedWhenFollowNotExist() {
		UnFollowUserRequest request = new UnFollowUserRequest();
		request.setUserId(1L);
		request.setTargetUserId(3L);
		assertException(InvalidArgumentException.class, () -> {
			followRelationService.unFollowUser(request);
		});
	}
}
