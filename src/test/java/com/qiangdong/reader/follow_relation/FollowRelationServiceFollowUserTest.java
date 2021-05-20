package com.qiangdong.reader.follow_relation;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.FollowRelationDto;
import com.qiangdong.reader.dto.MessageDto;
import com.qiangdong.reader.entity.FollowRelation;
import com.qiangdong.reader.enums.message.MessageTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.follow_relation.FollowUserRequest;
import com.qiangdong.reader.request.message.ListMessageByTypeRequest;
import com.qiangdong.reader.serviceImpl.FollowRelationServiceImpl;
import com.qiangdong.reader.serviceImpl.MessageServiceImpl;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FollowRelationServiceFollowUserTest extends BaseTest {
	@Autowired
	private FollowRelationServiceImpl followRelationService;

	@Autowired
	private MessageServiceImpl messageService;

	@Test
	public void followUserSuccessful() {
		FollowUserRequest request = new FollowUserRequest();
		request.setUserId(1L);
		request.setTargetUserId(3L);
		FollowRelation followRelation = followRelationService.followUser(request).getData();
		assertThat(followRelation.getId()).isGreaterThan(0L);

		ListMessageByTypeRequest listMessageByTypeRequest = new ListMessageByTypeRequest();
		listMessageByTypeRequest.setUserId(3L);
		listMessageByTypeRequest.setMessageType(MessageTypeEnum.FOLLOW);
		List<MessageDto> messageDtos = messageService.listMessageByType(listMessageByTypeRequest).getList();
		assertThat(messageDtos.size()).isEqualTo(1);
		FollowRelationDto followRelationDto = (FollowRelationDto) messageDtos.get(0).getMessage();
		assertThat(followRelationDto.getFollowerId()).isEqualTo(1L);

		request.setUserId(3L);
		request.setTargetUserId(1L);
		followRelationService.followUser(request);

		FollowRelation entity = followRelationService.getById(followRelation.getId());
		assertThat(entity.getFollowEach()).isTrue();
	}

	@Test
	public void followUserFailedWhenTargetUserNotExist() {
		FollowUserRequest request = new FollowUserRequest();
		request.setUserId(1L);
		request.setTargetUserId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			followRelationService.followUser(request);
		});
	}
}
