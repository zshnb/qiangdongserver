package com.qiangdong.reader.user_activity;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_activity.AgreeActivityRequest;
import com.qiangdong.reader.request.user_activity.GetUserActivityRequest;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserActivityServiceCancelAgreeActivityTest extends BaseTest {
	@Autowired
	private UserActivityServiceImpl userActivityService;

	@Test
	public void cancelAgreeActivitySuccessful() {
		AgreeActivityRequest request = new AgreeActivityRequest();
		request.setUserId(1L);
		request.setActivityId(1L);
		userActivityService.agreeActivity(request);
		GetUserActivityRequest getUserActivityRequest = new GetUserActivityRequest();
		getUserActivityRequest.setUserId(1L);
		getUserActivityRequest.setUserActivityId(1L);
		UserActivityDto userActivityDto = userActivityService.getUserActivity(getUserActivityRequest).getData();
		assertThat(userActivityDto.getAgree()).isTrue();
		assertThat(userActivityDto.getActivityData().getCreateActivity().getAgreeCount()).isEqualTo(1);

		userActivityService.cancelAgreeActivity(getUserActivityRequest, new UserActivity());
		userActivityDto = userActivityService.getUserActivity(getUserActivityRequest).getData();
		assertThat(userActivityDto.getAgree()).isNotTrue();
		assertThat(userActivityDto.getActivityData().getCreateActivity().getAgreeCount()).isEqualTo(0);
	}

	@Test
	public void cancelAgreeActivityFailedWhenActivityNotExist() {
		GetUserActivityRequest request = new GetUserActivityRequest();
		request.setUserId(1L);
		request.setUserActivityId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			userActivityService.cancelAgreeActivity(request, new UserActivity());
		});
	}

	@Test
	public void cancelAgreeActivityFailedWhenNoAgree() {
		GetUserActivityRequest request = new GetUserActivityRequest();
		request.setUserId(1L);
		request.setUserActivityId(1L);
		assertException(InvalidArgumentException.class, () -> {
			userActivityService.cancelAgreeActivity(request, new UserActivity());
		});
	}
}
