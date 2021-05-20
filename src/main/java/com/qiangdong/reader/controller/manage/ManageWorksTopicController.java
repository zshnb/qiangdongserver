package com.qiangdong.reader.controller.manage;


import com.qiangdong.reader.dto.WorksTopicDto;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.WorksTopic;
import com.qiangdong.reader.request.works_topic.*;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.works_topic.GetWorksTopicResponse;
import com.qiangdong.reader.serviceImpl.WorksTopicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
 * @since 2020-06-23
 */
@RestController
@RequestMapping("/manage/works-topic")
public class ManageWorksTopicController {

	@Autowired
	private WorksTopicServiceImpl topicService;

	@PostMapping("/add-update")
	public Response<WorksTopic> addOrUpdateTopic(@RequestBody AddOrUpdateWorksTopicRequest request){
		return topicService.addOrUpdateTopic(request, new Type());
	}

	@PostMapping("/list")
	public PageResponse<WorksTopicDto> listTopic(@RequestBody ListWorksTopicRequest request){
		return topicService.listTopic(request);
	}

	@PostMapping("/detail")
	public GetWorksTopicResponse getTopic(@RequestBody GetTopicRequest request){
		return topicService.getTopic(request);
	}

	@DeleteMapping("/{topicId}")
	public Response<String> deleteTopic(@RequestBody DeleteWorksTopicRequest request){
		return topicService.deleteTopic(request);
	}

	@PostMapping("/add-works")
	public Response<String> addTopicWorks(@RequestBody AddTopicWorksRequest request) {
		return topicService.addTopicWorks(request, new WorksTopic());
	}

}
