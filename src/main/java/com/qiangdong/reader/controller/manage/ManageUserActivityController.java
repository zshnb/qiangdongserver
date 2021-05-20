package com.qiangdong.reader.controller.manage;

import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.request.user_activity.DeleteUserActivityRequest;
import com.qiangdong.reader.request.user_activity.GetUserActivityRequest;
import com.qiangdong.reader.request.user_activity.ChangeActivityTopStatusRequest;
import com.qiangdong.reader.request.user_activity.ListUserActivityRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/user-activity")
public class ManageUserActivityController {

	@Autowired
	private UserActivityServiceImpl activityService;

	@PostMapping("/list")
	public PageResponse<UserActivityDto> listUserActivity(@RequestBody ListUserActivityRequest request) {
		return activityService.listActivityByManager(request);
	}

	@PostMapping("/detail/{userActivityId}")
	public Response<UserActivityDto> getUserActivity(@RequestBody GetUserActivityRequest request) {
		return activityService.getUserActivity(request);
	}

	@DeleteMapping("/activity")
	public Response<String> deleteUserActivity(@RequestBody DeleteUserActivityRequest request) {
		return activityService.deleteActivityByManager(request);
	}

	@PostMapping("/change-top")
	public Response<UserActivity> changeUserActivityTopStatus(@RequestBody ChangeActivityTopStatusRequest request) {
		return activityService.changeTopStatus(request, new UserActivity());
	}
}
