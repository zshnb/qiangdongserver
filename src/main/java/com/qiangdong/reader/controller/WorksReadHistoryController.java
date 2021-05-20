package com.qiangdong.reader.controller;


import com.qiangdong.reader.dto.WorksReadHistoryDto;
import com.qiangdong.reader.entity.WorksReadHistory;
import com.qiangdong.reader.request.works_history.AddOrUpdateWorksHistoryRequest;
import com.qiangdong.reader.request.works_history.ListWorksReadHistoryRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.WorksReadHistoryServiceImpl;
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
 * @since 2020-07-20
 */
@RestController
@RequestMapping("/works-read-history")
public class WorksReadHistoryController {
	@Autowired
	private WorksReadHistoryServiceImpl worksReadHistoryService;

	@PostMapping("/add-update")
	public Response<WorksReadHistory> addOrUpdateWorksReadHistory(@RequestBody AddOrUpdateWorksHistoryRequest request) {
		return worksReadHistoryService.addOrUpdateWorksReadHistory(request);
	}

	@PostMapping("/list")
	public PageResponse<WorksReadHistoryDto> listWorksReadHistory(@RequestBody ListWorksReadHistoryRequest request) {
		return worksReadHistoryService.listWorksReadHistory(request);
	}
}
