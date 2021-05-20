package com.qiangdong.reader.statistics;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.statistics.CreditStatisticsDto;
import com.qiangdong.reader.enums.statistics.DateUnitEnum;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.statistics.ListCreditStatisticsRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.StatisticsServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StatisticsServiceListCreditStatisticsTest extends BaseTest {
	@Autowired
	private StatisticsServiceImpl statisticsService;

	@Test
	public void listDayStatisticsSuccessful() {
		ListCreditStatisticsRequest request = new ListCreditStatisticsRequest();
		request.setUserId(adminUserId);
		request.setDateUnit(DateUnitEnum.DAY);
		PageResponse<CreditStatisticsDto> response = statisticsService.listCreditStatistics(request);
		assertThat(response.getList().size()).isEqualTo(30);
	}

	@Test
	public void listMonthStatisticsSuccessful() {
		ListCreditStatisticsRequest request = new ListCreditStatisticsRequest();
		request.setUserId(adminUserId);
		request.setDateUnit(DateUnitEnum.MONTH);
		PageResponse<CreditStatisticsDto> response = statisticsService.listCreditStatistics(request);
		assertThat(response.getList().size()).isEqualTo(12);
	}

	@Test
	public void listYearStatisticsSuccessful() {
		ListCreditStatisticsRequest request = new ListCreditStatisticsRequest();
		request.setUserId(adminUserId);
		request.setDateUnit(DateUnitEnum.YEAR);
		PageResponse<CreditStatisticsDto> response = statisticsService.listCreditStatistics(request);
		assertThat(response.getList().size()).isEqualTo(5);
	}

	@Test
	public void listDayStatisticsFailedWhenNoPermission() {
		assertException(PermissionDenyException.class, () -> {
			statisticsService.listCreditStatistics(new ListCreditStatisticsRequest());
		});
	}
}
