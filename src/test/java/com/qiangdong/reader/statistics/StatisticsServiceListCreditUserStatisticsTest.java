package com.qiangdong.reader.statistics;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.statistics.CreditUserStatisticsDto;
import com.qiangdong.reader.enums.statistics.DateUnitEnum;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.statistics.ListCreditUserStatisticsRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.StatisticsServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StatisticsServiceListCreditUserStatisticsTest extends BaseTest {
	@Autowired
	private StatisticsServiceImpl statisticsService;

	@Test
	public void listDayStatisticsSuccessful() {
		ListCreditUserStatisticsRequest request = new ListCreditUserStatisticsRequest();
		request.setUserId(adminUserId);
		request.setDateUnit(DateUnitEnum.DAY);
		PageResponse<CreditUserStatisticsDto> response = statisticsService.listCreditUserStatistics(request);
		assertThat(response.getList().size()).isEqualTo(30);
	}

	@Test
	public void listMonthStatisticsSuccessful() {
		ListCreditUserStatisticsRequest request = new ListCreditUserStatisticsRequest();
		request.setUserId(adminUserId);
		request.setDateUnit(DateUnitEnum.MONTH);
		PageResponse<CreditUserStatisticsDto> response = statisticsService.listCreditUserStatistics(request);
		assertThat(response.getList().size()).isEqualTo(12);
	}

	@Test
	public void listYearStatisticsSuccessful() {
		ListCreditUserStatisticsRequest request = new ListCreditUserStatisticsRequest();
		request.setUserId(adminUserId);
		request.setDateUnit(DateUnitEnum.YEAR);
		PageResponse<CreditUserStatisticsDto> response = statisticsService.listCreditUserStatistics(request);
		assertThat(response.getList().size()).isEqualTo(5);
	}

	@Test
	public void listDayStatisticsFailedWhenNoPermission() {
		assertException(PermissionDenyException.class, () -> {
			statisticsService.listCreditUserStatistics(new ListCreditUserStatisticsRequest());
		});
	}
}
