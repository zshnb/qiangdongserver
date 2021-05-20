package com.qiangdong.reader.controller.manage;

import com.qiangdong.reader.dto.statistics.CreditStatisticsDto;
import com.qiangdong.reader.dto.statistics.CreditUserStatisticsDto;
import com.qiangdong.reader.dto.statistics.DauStatisticsDto;
import com.qiangdong.reader.dto.statistics.RegisterStatisticsDto;
import com.qiangdong.reader.dto.statistics.StatisticsDataOverviewDto;
import com.qiangdong.reader.dto.statistics.UserCreditRankDto;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.statistics.ListCreditStatisticsRequest;
import com.qiangdong.reader.request.statistics.ListCreditUserStatisticsRequest;
import com.qiangdong.reader.request.statistics.ListDauStatisticsRequest;
import com.qiangdong.reader.request.statistics.ListRegisterStatisticsRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.StatisticsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/statistics")
public class ManageStatisticsController {
	@Autowired
	private StatisticsServiceImpl statisticsService;

	@PostMapping("/overview")
	public Response<StatisticsDataOverviewDto> getStatisticsDataOverview(@RequestBody BaseRequest request) {
		return statisticsService.getStatisticsDataOverview(request);
	}

	@PostMapping("/list-register-statistics")
	public PageResponse<RegisterStatisticsDto> listRegisterStatistics(
		@RequestBody ListRegisterStatisticsRequest request) {
		return statisticsService.listRegisterStatistics(request);
	}

	@PostMapping("/list-dau-statistics")
	public PageResponse<DauStatisticsDto> listDauStatistics(
		@RequestBody ListDauStatisticsRequest request) {
		return statisticsService.listDauStatistics(request);
	}

	@PostMapping("/list-credit-statistics")
	public PageResponse<CreditStatisticsDto> listCreditStatistics(
		@RequestBody ListCreditStatisticsRequest request) {
		return statisticsService.listCreditStatistics(request);
	}

	@PostMapping("/list-credit-user-statistics")
	public PageResponse<CreditUserStatisticsDto> listCreditUserStatistics(
		@RequestBody ListCreditUserStatisticsRequest request) {
		return statisticsService.listCreditUserStatistics(request);
	}

	@PostMapping("/list-user-credit-rank")
	public PageResponse<UserCreditRankDto> listUserCreditRank(@RequestBody BaseRequest request) {
		return statisticsService.listUserCreditRank(request);
	}
}
