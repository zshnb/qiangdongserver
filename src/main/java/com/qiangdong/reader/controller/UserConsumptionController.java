package com.qiangdong.reader.controller;


import com.qiangdong.reader.dto.WorksIncomeUserRankingDto;
import com.qiangdong.reader.request.user.ListWorksIncomeUserRankingRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.UserConsumptionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户消费表-消费虚拟货币 前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/user-consumption")
public class UserConsumptionController {
	@Autowired
	private UserConsumptionServiceImpl userConsumptionService;

	@PostMapping("/user-ranking")
	public PageResponse<WorksIncomeUserRankingDto> listWorksIncomeUserRanking(
		@RequestBody ListWorksIncomeUserRankingRequest request) {
		return userConsumptionService.listWorksWorksIncomeUserRanking(request);
	}
}
