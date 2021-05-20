package com.qiangdong.reader.controller;


import com.qiangdong.reader.dto.read_activity_record.ActivityReadHistoryDto;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.ActivityReadHistoryServiceImpl;
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
 * @since 2020-07-17
 */
@RestController
@RequestMapping("/activity-read-history")
public class ActivityReadHistoryController {

	@Autowired
	private ActivityReadHistoryServiceImpl activityReadHistoryService;

	@PostMapping("/list")
	public PageResponse<ActivityReadHistoryDto> listActivityReadHistory(@RequestBody BaseRequest request){
		return activityReadHistoryService.listHistory(request);
	}

	@PostMapping("/clean")
	public Response<String> cleanActivityReadHistory(@RequestBody BaseRequest request) {
		return activityReadHistoryService.cleanHistory(request);
	}
}
