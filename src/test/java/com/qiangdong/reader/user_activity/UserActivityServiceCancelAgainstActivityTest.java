package com.qiangdong.reader.user_activity;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.common.UserActivityConstant;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_activity.AgainstActivityRequest;
import com.qiangdong.reader.request.user_activity.GetUserActivityRequest;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

public class UserActivityServiceCancelAgainstActivityTest extends BaseTest {
	@Autowired
	private UserActivityServiceImpl userActivityService;

	@Autowired
	private RedisTemplate<String, Long> redisTemplate;

	@Test
	public void cancelAgainstActivitySuccessful() {
		AgainstActivityRequest request = new AgainstActivityRequest();
		request.setUserId(1L);
		request.setActivityId(1L);
		userActivityService.againstActivity(request);

		GetUserActivityRequest getUserActivityRequest = new GetUserActivityRequest();
		getUserActivityRequest.setUserId(1L);
		getUserActivityRequest.setUserActivityId(1L);
		userActivityService.cancelAgainstActivity(getUserActivityRequest, new UserActivity());

		ZSetOperations<String, Long> zSetOperations = redisTemplate.opsForZSet();
		long size = zSetOperations.size(String.format(UserActivityConstant.KEY_ACTIVITY_AGREE, 1));
		assertThat(size).isEqualTo(0L);
	}

	@Test
	public void cancelAgainstActivityFailedWhenActivityNotExist() {
		GetUserActivityRequest request = new GetUserActivityRequest();
		request.setUserId(1L);
		request.setUserActivityId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			userActivityService.cancelAgainstActivity(request, new UserActivity());
		});
	}

	@Test
	public void cancelAgainstActivityFailedWhenNoAgree() {
		GetUserActivityRequest request = new GetUserActivityRequest();
		request.setUserId(1L);
		request.setUserActivityId(1L);
		assertException(InvalidArgumentException.class, () -> {
			userActivityService.cancelAgainstActivity(request, new UserActivity());
		});
	}
}
