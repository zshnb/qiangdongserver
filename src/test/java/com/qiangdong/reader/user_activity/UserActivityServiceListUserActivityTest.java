package com.qiangdong.reader.user_activity;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.request.user_activity.AgreeActivityRequest;
import com.qiangdong.reader.request.user_activity.ListUserActivityRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserActivityServiceListUserActivityTest extends BaseTest {
	@Autowired
	private UserActivityServiceImpl userActivityService;

	@Test
	public void listSuccessful() {
		AgreeActivityRequest agreeActivityRequest = new AgreeActivityRequest();
		agreeActivityRequest.setUserId(2L);
		agreeActivityRequest.setActivityId(1L);
		userActivityService.agreeActivity(agreeActivityRequest);

		ListUserActivityRequest request = new ListUserActivityRequest();
		request.setTargetUserId(1L);
		PageResponse<UserActivityDto> response = userActivityService.listUserActivity(request);
		assertThat(response.getList().size()).isEqualTo(2);
	}
}
