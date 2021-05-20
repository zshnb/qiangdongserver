package com.qiangdong.reader.user_activity;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.read_activity_record.ActivityReadHistoryDto;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_activity.AgreeActivityRequest;
import com.qiangdong.reader.request.user_activity.GetUserActivityRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.ActivityReadHistoryServiceImpl;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserActivityServiceGetActivityTest extends BaseTest {
	@Autowired
	private UserActivityServiceImpl activityService;

	@Autowired
	private ActivityReadHistoryServiceImpl activityReadHistoryService;

	@Test
	public void getActivitySuccessful(){
		AgreeActivityRequest agreeActivityRequest = new AgreeActivityRequest();
		agreeActivityRequest.setUserId(5L);
		agreeActivityRequest.setActivityId(1L);
		activityService.agreeActivity(agreeActivityRequest);

		GetUserActivityRequest request = new GetUserActivityRequest();
		request.setUserId(5L);
		request.setUserActivityId(1L);
		Response<UserActivityDto> response = activityService.getUserActivity(request);
		assertThat(response.getData().getUsername()).isEqualTo("user1");
		assertThat(response.getData().getFollow()).isNotTrue();
		assertThat(response.getData().getAgree()).isTrue();

		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserId(5L);
		PageResponse<ActivityReadHistoryDto> activityReadHistoryPageResponse =
			activityReadHistoryService.listHistory(baseRequest);
		assertThat(activityReadHistoryPageResponse.getList().size()).isEqualTo(1);
	}

	@Test
	public void getActivityFailedWhenActivityNotExist() {
		GetUserActivityRequest request = new GetUserActivityRequest();
		request.setUserId(5L);
		request.setUserActivityId(-1L);
		assertException(InvalidArgumentException.class, () -> activityService.getUserActivity(request));
	}
}
