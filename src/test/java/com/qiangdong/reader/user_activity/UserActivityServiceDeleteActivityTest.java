package com.qiangdong.reader.user_activity;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_activity.DeleteActivityRequest;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserActivityServiceDeleteActivityTest extends BaseTest {
	@Autowired
	private UserActivityServiceImpl userActivityService;

	@Test
	public void deleteActivitySuccessful() {
		DeleteActivityRequest request = new DeleteActivityRequest();
		request.setActivityId(1L);
		userActivityService.deleteActivity(request);

		UserActivity userActivity = userActivityService.getById(1L);
		assertThat(userActivity).isNull();
	}

	@Test
	public void deleteActivityFailedWhenActivityNotExist() {
		DeleteActivityRequest request = new DeleteActivityRequest();
		request.setActivityId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			userActivityService.deleteActivity(request);
		});
	}
}
