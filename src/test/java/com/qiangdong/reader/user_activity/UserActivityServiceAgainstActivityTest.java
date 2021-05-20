package com.qiangdong.reader.user_activity;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.common.UserActivityConstant;
import com.qiangdong.reader.dao.UserActivityMapper;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_activity.AgainstActivityRequest;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

public class UserActivityServiceAgainstActivityTest extends BaseTest {
	@Autowired
	private UserActivityServiceImpl userActivityService;

//	@Test
//	public void againstActivitySuccessful() {
//		AgainstActivityRequest request = new AgainstActivityRequest();
//		request.setUserId(1L);
//		request.setActivityId(1L);
//		userActivityService.againstActivity(request);
//
//		ZSetOperations<String, Long> zSetOperations = redisTemplate.opsForZSet();
//		long size = zSetOperations.size(String.format(UserActivityConstant.KEY_ACTIVITY_AGAINST, 1));
//		assertThat(size).isEqualTo(1L);
//		activitySchedule.synchronizeAgreeAndAgainst();
//
//		UserActivity userActivity = userActivityMapper.findById(1L);
//		assertThat(userActivity.getActivityData().getCreateActivity().getAgainstCount()).isEqualTo(6);
//	}

	@Test
	public void againstActivityFailedWhenActivityNotExist() {
		AgainstActivityRequest request = new AgainstActivityRequest();
		request.setUserId(1L);
		request.setActivityId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			userActivityService.againstActivity(request);
		});
	}

	@Test
	public void againstActivityFailedWhenTwice() {
		AgainstActivityRequest request = new AgainstActivityRequest();
		request.setUserId(1L);
		request.setActivityId(1L);
		userActivityService.againstActivity(request);
		assertException(InvalidArgumentException.class, () -> userActivityService.againstActivity(request));
	}
}
