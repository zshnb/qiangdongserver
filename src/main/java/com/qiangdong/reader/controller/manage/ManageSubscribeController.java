package com.qiangdong.reader.controller.manage;

import com.qiangdong.reader.dto.SubscribeDto;
import com.qiangdong.reader.request.subscribe.GetWorksStatisticsRequest;
import com.qiangdong.reader.request.subscribe.ListWorksChapterSubscribeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.subscribe.WorksStatisticsResponse;
import com.qiangdong.reader.serviceImpl.SubscribeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/subscribe")
public class ManageSubscribeController {

	@Autowired
	private SubscribeServiceImpl subscribeService;

	@PostMapping("/works-statistics")
	public WorksStatisticsResponse getWorksStatistics(@RequestBody GetWorksStatisticsRequest request){
		return subscribeService.getWorksStatistics(request);
	}

	@PostMapping("/list-chapter")
	public PageResponse<SubscribeDto> listWorksChapterSubscribe(@RequestBody ListWorksChapterSubscribeRequest request) {
		return subscribeService.listChapterSubscribe(request);
	}

}
