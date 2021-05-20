package com.qiangdong.reader.user_activity;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.request.user_activity.ListUserActivityRequest;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserActivityServiceListActivityByManagerTest extends BaseTest {
	@Autowired
	private UserActivityServiceImpl activityService;

	@Test
	public void listActivitySuccessful(){
		ListUserActivityRequest request = new ListUserActivityRequest();
		request.setUserId(5L);
		request.setSearchParam("content1");
		List<UserActivityDto> listUserAcitivity = activityService.listActivityByManager(request).getList();
		assertThat(listUserAcitivity.get(0).getUsername()).isEqualTo("user1");
		assertThat(listUserAcitivity.get(0).getTop()).isTrue();
		assertThat(listUserAcitivity.get(0).getActivityData().getCreateActivity().getContent()).isEqualTo("content1");
	}
}
