package com.qiangdong.reader.controller;


import com.qiangdong.reader.dto.TopicDto;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.Topic;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.topic.AddOrUpdateTopicRequest;
import com.qiangdong.reader.request.topic.FollowOrUnFollowTopicRequest;
import com.qiangdong.reader.request.topic.GetTopicRequest;
import com.qiangdong.reader.request.topic.ListHeatUserActivityRequest;
import com.qiangdong.reader.request.topic.ListNewestUserActivityRequest;
import com.qiangdong.reader.request.topic.ListSimilarTopicRequest;
import com.qiangdong.reader.request.topic.SearchTopicRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.search.TopicForSearch;
import com.qiangdong.reader.serviceImpl.TopicServiceImpl;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户协议 前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-16
 */
@RestController
@RequestMapping("/topic")
public class TopicController {
	private final TopicServiceImpl topicService;

	public TopicController(TopicServiceImpl topicService) {
		this.topicService = topicService;
	}

	@PostMapping("/add-update")
	public Response<Topic> addTopic(@RequestBody @Valid AddOrUpdateTopicRequest request) {
		return topicService.addOrUpdateTopic(request);
	}

	@PostMapping("/detail/{topicId}")
	public Response<TopicDto> getTopic(@RequestBody GetTopicRequest request) {
		return topicService.getTopic(request);
	}

	@PostMapping("/list-similar")
	public PageResponse<TopicDto> listSimilarTopic(@RequestBody ListSimilarTopicRequest request) {
		return topicService.listSimilarTopic(request);
	}

	@PostMapping("/list")
	public PageResponse<Topic> listTopic(@RequestBody BaseRequest request) {
		return topicService.listTopic(request);
	}

	@PostMapping("/hot-topics")
	public PageResponse<Topic> listHotTopic(@RequestBody BaseRequest request) {
		return topicService.listHotTopic(request);
	}

	@PostMapping("/search")
	public PageResponse<TopicForSearch> searchTopic(@RequestBody SearchTopicRequest request) {
		return topicService.searchTopic(request);
	}

	@PostMapping("/list-newest")
	public PageResponse<TopicDto> listNewestTopic(@RequestBody BaseRequest request) {
		return topicService.listNewestTopic(request);
	}

	@PostMapping("/follow/{topicId}")
	public Response<String> followTopic(@RequestBody FollowOrUnFollowTopicRequest request) {
		return topicService.followTopic(request, new Topic());
	}

	@PostMapping("/un-follow/{topicId}")
	public Response<String> unFollowTopic(@RequestBody FollowOrUnFollowTopicRequest request) {
		return topicService.unFollowTopic(request, new Topic());
	}

	@PostMapping("/list-heat-activity")
	public PageResponse<UserActivityDto> listHeatUserActivity(@RequestBody ListHeatUserActivityRequest request) {
		return topicService.listHeatUserActivity(request, new Topic());
	}

	@PostMapping("/list-newest-activity")
	public PageResponse<UserActivityDto> listNewestUserActivity(@RequestBody ListNewestUserActivityRequest request) {
		return topicService.listNewestUserActivity(request, new Topic());
	}
}
