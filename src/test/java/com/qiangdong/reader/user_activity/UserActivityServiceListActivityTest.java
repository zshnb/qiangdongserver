package com.qiangdong.reader.user_activity;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_activity.AgreeActivityRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserActivityServiceListActivityTest extends BaseTest {
	@Autowired
	private UserActivityServiceImpl userActivityService;

	@Test
	public void listSuccessful() {
		BaseRequest request = new BaseRequest();
		request.setUserId(1L);
		PageResponse<UserActivityDto> response = userActivityService.listActivity(request);
		assertThat(response.getList().size()).isEqualTo(2);

		AgreeActivityRequest agreeActivityRequest = new AgreeActivityRequest();
		agreeActivityRequest.setUserId(1L);
		agreeActivityRequest.setActivityId(1L);
		userActivityService.agreeActivity(agreeActivityRequest);

		response = userActivityService.listActivity(request);
		UserActivityDto userActivityDto = response.getList().stream()
			.filter(it -> it.getId().equals(1L)).findFirst().get();
		assertThat(userActivityDto.getAgree()).isTrue();
	}
}
