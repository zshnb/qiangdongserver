package com.qiangdong.reader.follow_relation;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.FollowRelationDto;
import com.qiangdong.reader.request.follow_relation.ListFollowUserRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.FollowRelationServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FollowRelationServiceListFollowUserTest extends BaseTest {
	@Autowired
	private FollowRelationServiceImpl followRelationService;

	@Test
	public void listSuccessful() {
		ListFollowUserRequest request = new ListFollowUserRequest();
		request.setTargetUserId(1L);
		PageResponse<FollowRelationDto> response = followRelationService.listFollowUser(request);
		assertThat(response.getList().size()).isEqualTo(1);

		FollowRelationDto followRelationDto = response.getList().get(0);
		assertThat(followRelationDto.getFollowedId()).isEqualTo(2L);
		assertThat(followRelationDto.getFollowEach()).isTrue();
	}
}
