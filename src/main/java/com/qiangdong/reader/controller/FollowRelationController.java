package com.qiangdong.reader.controller;


import com.qiangdong.reader.dto.FollowRelationDto;
import com.qiangdong.reader.entity.FollowRelation;
import com.qiangdong.reader.request.follow_relation.FollowUserRequest;
import com.qiangdong.reader.request.follow_relation.ListFansUserRequest;
import com.qiangdong.reader.request.follow_relation.ListFollowUserRequest;
import com.qiangdong.reader.request.follow_relation.UnFollowUserRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.FollowRelationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 关注表 前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-25
 */
@RestController
@RequestMapping("/follow-relation")
public class FollowRelationController {
	@Autowired
	private FollowRelationServiceImpl followRelationService;

	@PostMapping("/follow-user/{targetUserId}")
	public Response<FollowRelation> followUser(@RequestBody FollowUserRequest request) {
		return followRelationService.followUser(request);
	}

	@PostMapping("/un-follow-user/{targetUserId}")
	public Response<String> unFollowUser(@RequestBody UnFollowUserRequest request) {
		return followRelationService.unFollowUser(request);
	}

	@PostMapping("/list-follow-user")
	public PageResponse<FollowRelationDto> listFollowUser(@RequestBody ListFollowUserRequest request) {
		return followRelationService.listFollowUser(request);
	}

	@PostMapping("/list-fans-user")
	public PageResponse<FollowRelationDto> listFansUser(@RequestBody ListFansUserRequest request) {
		return followRelationService.listFansUser(request);
	}
}
