package com.qiangdong.reader.user_activity;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_activity.ChangeActivityTopStatusRequest;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserActivityServiceChangeActivityTopTest extends BaseTest {

	@Autowired
	private UserActivityServiceImpl activityService;

	@Test
	public void changeTopSuccessful() {
		ChangeActivityTopStatusRequest request = new ChangeActivityTopStatusRequest();
		request.setUserActivityId(1L);
		request.setTop(false);
		UserActivity userActivity = activityService.changeTopStatus(request, new UserActivity()).getData();
		assertThat(userActivity.getTop()).isNotTrue();
	}

	@Test
	public void changeTopWhenActivityNoExist() {
		ChangeActivityTopStatusRequest request = new ChangeActivityTopStatusRequest();
		request.setUserActivityId(-1L);
		assertException(InvalidArgumentException.class, () ->activityService.changeTopStatus(request, new UserActivity()));
	}
}
