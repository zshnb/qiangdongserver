package com.qiangdong.reader.user_activity;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_activity.DeleteUserActivityRequest;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserActivityServiceDeleteActivityByManagerTest extends BaseTest {

	@Autowired
	private UserActivityServiceImpl activityService;

	@Test
	public void deleteActivitySuccessful() {
		DeleteUserActivityRequest request = new DeleteUserActivityRequest();
		request.setUserId(adminUserId);
		List<Long> activityIds = new ArrayList<>();
		activityIds.add(1L);
		activityIds.add(2L);
		request.setActivityIds(activityIds);
		activityService.deleteActivityByManager(request);
		UserActivity activity = activityService.getById(1L);
		assertThat(activity).isNull();
		activity = activityService.getById(2L);
		assertThat(activity).isNull();
	}

	@Test
	public void deleteActivityFailedWhenNotExist() {
		DeleteUserActivityRequest request = new DeleteUserActivityRequest();
		request.setUserId(adminUserId);
		List<Long> activityIds = new ArrayList<>();
		activityIds.add(1L);
		activityIds.add(2L);
		activityIds.add(-2L);
		request.setActivityIds(activityIds);
		assertException(InvalidArgumentException.class, () ->activityService.deleteActivityByManager(request));

	}
}
