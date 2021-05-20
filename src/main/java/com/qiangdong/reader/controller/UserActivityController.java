package com.qiangdong.reader.controller;


import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_activity.*;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户动态表 前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-25
 */
@RestController
@RequestMapping("/user-activity")
public class UserActivityController {
	@Autowired
	private UserActivityServiceImpl userActivityService;

	@PostMapping("/add-update")
	public Response<UserActivity> addOrUpdateActivity(@RequestBody AddOrUpdateActivityRequest request) {
		return userActivityService.addOrUpdateActivity(request, new User());
	}

	@PostMapping("/share")
	public Response<UserActivity> shareUserActivity(@RequestBody ShareUserActivityRequest request) {
		return userActivityService.shareUserActivity(request);
	}

	@PostMapping("/list")
	public PageResponse<UserActivityDto> listActivity(@RequestBody BaseRequest request) {
		return userActivityService.listActivity(request);
	}

	@PostMapping("/list-target-user")
	public PageResponse<UserActivityDto> listUserActivity(@RequestBody ListUserActivityRequest request) {
		return userActivityService.listUserActivity(request);
	}

	@PostMapping("/list-recommend")
	public PageResponse<UserActivityDto> listRecommendActivity(@RequestBody BaseRequest request) {
		return userActivityService.listRecommendActivity(request);
	}

	@PostMapping("/detail/{userActivityId}")
	public Response<UserActivityDto> getUserActivity(@RequestBody GetUserActivityRequest request) {
		return userActivityService.getUserActivity(request);
	}

	@DeleteMapping("/{activityId}")
	public Response<String> deleteActivity(@RequestBody DeleteActivityRequest request) {
		return userActivityService.deleteActivity(request);
	}

	@PostMapping("/search")
	public PageResponse<UserActivityDto> searchUserActivity(@RequestBody SearchUserActivityRequest request) {
		return userActivityService.searchUserActivity(request);
	}

	@PostMapping("/list-follow-user-activity")
	public PageResponse<UserActivityDto> listFollowUserActivity(@RequestBody BaseRequest request) {
		return userActivityService.listFollowUserActivity(request);
	}

	@PostMapping("/agree")
	public Response<Integer> agreeActivity(@RequestBody AgreeActivityRequest request) {
		return userActivityService.agreeActivity(request);
	}

	@PostMapping("/against")
	public Response<Integer> againstActivity(@RequestBody AgainstActivityRequest request) {
		return userActivityService.againstActivity(request);
	}

	@PostMapping("/cancel-agree")
	public Response<Integer> cancelAgreeActivity(@RequestBody GetUserActivityRequest request) {
		return userActivityService.cancelAgreeActivity(request, new UserActivity());
	}

	@PostMapping("/cancel-against")
	public Response<Integer> againstActivity(@RequestBody GetUserActivityRequest request) {
		return userActivityService.cancelAgainstActivity(request, new UserActivity());
	}

	@PostMapping("/comment-refer")
	public Response<UserActivityDto> getUserActivityByComment(@RequestBody GetUserActivityByCommentRequest request) {
		return userActivityService.getUserActivityByComment(request, new Comment());
	}
}
