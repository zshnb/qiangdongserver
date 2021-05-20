package com.qiangdong.reader.follow_relation;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.FollowRelationDto;
import com.qiangdong.reader.request.follow_relation.ListFansUserRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.FollowRelationServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FollowRelationServiceListFansUserTest extends BaseTest {
	@Autowired
	private FollowRelationServiceImpl followRelationService;

	@Test
	public void listSuccessful() {
		ListFansUserRequest request = new ListFansUserRequest();
		request.setTargetUserId(1L);
		PageResponse<FollowRelationDto> response = followRelationService.listFansUser(request);
		assertThat(response.getList().size()).isEqualTo(1);

		FollowRelationDto followRelationDto = response.getList().get(0);
		assertThat(followRelationDto.getFollowerId()).isEqualTo(2L);
		assertThat(followRelationDto.getFollowEach()).isTrue();
	}
}
