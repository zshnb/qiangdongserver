package com.qiangdong.reader.user_activity;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user_activity.ShareActivity;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_activity.GetUserActivityRequest;
import com.qiangdong.reader.request.user_activity.ShareUserActivityRequest;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserActivityServiceShareActivityTest extends BaseTest {
	@Autowired
	private UserActivityServiceImpl userActivityService;

	@Test
	public void shareUserActivitySuccessful() {
		ShareActivity shareActivity = new ShareActivity();
		shareActivity.setReferActivityId(1L);
		ShareUserActivityRequest shareUserActivityRequest = new ShareUserActivityRequest();
		shareUserActivityRequest.setShareActivity(shareActivity);
		UserActivity shareUserActivity = userActivityService.shareUserActivity(shareUserActivityRequest).getData();
		assertThat(shareUserActivity.getActivityData().getShareActivity().getReferActivityId()).isEqualTo(1L);

		GetUserActivityRequest getUserActivityRequest = new GetUserActivityRequest();
		getUserActivityRequest.setUserActivityId(1L);
		UserActivityDto activityDto = userActivityService.getUserActivity(getUserActivityRequest).getData();
		assertThat(activityDto.getActivityData().getCreateActivity().getShareCount()).isEqualTo(2);

		shareActivity.setReferActivityId(shareUserActivity.getId());
		shareUserActivityRequest.setShareActivity(shareActivity);
		shareUserActivity = userActivityService.shareUserActivity(shareUserActivityRequest).getData();
		assertThat(shareUserActivity.getActivityData().getShareActivity().getReferActivityId()).isEqualTo(1L);

		activityDto = userActivityService.getUserActivity(getUserActivityRequest).getData();
		assertThat(activityDto.getActivityData().getCreateActivity().getShareCount()).isEqualTo(3);
	}

	@Test
	public void shareUserActivityFailedWhenActivityNotExist() {
		ShareUserActivityRequest shareUserActivityRequest = new ShareUserActivityRequest();
		assertException(InvalidArgumentException.class, () -> {
			userActivityService.shareUserActivity(shareUserActivityRequest);
		});
	}
}
