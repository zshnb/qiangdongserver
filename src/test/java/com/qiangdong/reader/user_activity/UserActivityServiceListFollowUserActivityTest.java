package com.qiangdong.reader.user_activity;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserActivityServiceListFollowUserActivityTest extends BaseTest {
	@Autowired
	private UserActivityServiceImpl userActivityService;

	@Test
	public void listSuccessful() {
		BaseRequest request = new BaseRequest();
		request.setUserId(2L);
		PageResponse<UserActivityDto> response = userActivityService.listFollowUserActivity(request);
		assertThat(response.getList().size()).isEqualTo(2);
		assertThat(response.getList().get(0).getUserId()).isEqualTo(1L);
	}
}
