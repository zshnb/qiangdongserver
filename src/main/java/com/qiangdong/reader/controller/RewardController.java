package com.qiangdong.reader.controller;


import com.qiangdong.reader.request.user.RewardComicRequest;
import com.qiangdong.reader.request.user.RewardNovelRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.RewardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/reward")
public class RewardController {
	@Autowired
	private RewardServiceImpl rewardService;

	@PostMapping("/reward-novel")
	public Response<Integer> rewardNovel(@RequestBody RewardNovelRequest request) {
		return rewardService.rewardNovel(request);
	}

	@PostMapping("/reward-comic")
	public Response<Integer> rewardComic(@RequestBody RewardComicRequest request) {
		return rewardService.rewardComic(request);
	}
}
