package com.qiangdong.reader.user_activity;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_activity.AgreeActivityRequest;
import com.qiangdong.reader.request.user_activity.GetUserActivityRequest;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserActivityServiceAgreeActivityTest extends BaseTest {
	@Autowired
	private UserActivityServiceImpl userActivityService;

	@Test
	public void agreeActivitySuccessful() {
		AgreeActivityRequest request = new AgreeActivityRequest();
		request.setUserId(1L);
		request.setActivityId(1L);
		userActivityService.agreeActivity(request);

		GetUserActivityRequest getUserActivityRequest = new GetUserActivityRequest();
		getUserActivityRequest.setUserActivityId(1L);
		getUserActivityRequest.setUserId(1L);
		UserActivityDto userActivityDto = userActivityService.getUserActivity(getUserActivityRequest).getData();
		assertThat(userActivityDto.getAgree()).isTrue();
		assertThat(userActivityDto.getActivityData().getCreateActivity().getAgreeCount()).isEqualTo(1);
	}

	@Test
	public void agreeActivityFailedWhenActivityNotExist() {
		AgreeActivityRequest request = new AgreeActivityRequest();
		request.setUserId(1L);
		request.setActivityId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			userActivityService.agreeActivity(request);
		});
	}

	@Test
	public void agreeActivityFailedWhenTwice() {
		AgreeActivityRequest request = new AgreeActivityRequest();
		request.setUserId(1L);
		request.setActivityId(1L);
		userActivityService.agreeActivity(request);
		assertException(InvalidArgumentException.class, () -> userActivityService.agreeActivity(request));
	}
}
