package com.qiangdong.reader.user_activity;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_activity_collection.AddUserActivityCollectionRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.UserActivityCollectionServiceImpl;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserActivityServiceListRecommendActivityTest extends BaseTest {
	@Autowired
	private UserActivityServiceImpl userActivityService;

	@Autowired
	private UserActivityCollectionServiceImpl userActivityCollectionService;

	@Test
	public void listSuccessful() {
		AddUserActivityCollectionRequest addUserActivityCollectionRequest = new AddUserActivityCollectionRequest();
		addUserActivityCollectionRequest.setUserId(1L);
		addUserActivityCollectionRequest.setUserActivityId(1L);
		userActivityCollectionService.addUserActivityCollection(addUserActivityCollectionRequest, new UserActivity());

		BaseRequest request = new BaseRequest();
		request.setUserId(1L);
		PageResponse<UserActivityDto> response = userActivityService.listRecommendActivity(request);
		assertThat(response.getList().size()).isEqualTo(3);
	}
}
