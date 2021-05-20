package com.qiangdong.reader.statistics;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.statistics.DauStatisticsDto;
import com.qiangdong.reader.dto.statistics.RegisterStatisticsDto;
import com.qiangdong.reader.entity.UserLoginCount;
import com.qiangdong.reader.enums.statistics.DateUnitEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.statistics.ListDauStatisticsRequest;
import com.qiangdong.reader.request.statistics.ListRegisterStatisticsRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.StatisticsServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StatisticsServiceListDauStatisticsTest extends BaseTest {
	@Autowired
	private StatisticsServiceImpl statisticsService;

	@Test
	public void listDayStatisticsSuccessful() {
		ListDauStatisticsRequest request = new ListDauStatisticsRequest();
		request.setUserId(adminUserId);
		request.setDateUnit(DateUnitEnum.DAY);
		PageResponse<DauStatisticsDto> response = statisticsService.listDauStatistics(request);
		assertThat(response.getList().size()).isEqualTo(30);
	}

	@Test
	public void listMonthStatisticsSuccessful() {
		ListDauStatisticsRequest request = new ListDauStatisticsRequest();
		request.setUserId(adminUserId);
		request.setDateUnit(DateUnitEnum.MONTH);
		PageResponse<DauStatisticsDto> response = statisticsService.listDauStatistics(request);
		assertThat(response.getList().size()).isEqualTo(12);
	}

	@Test
	public void listYearStatisticsSuccessful() {
		ListDauStatisticsRequest request = new ListDauStatisticsRequest();
		request.setUserId(adminUserId);
		request.setDateUnit(DateUnitEnum.YEAR);
		PageResponse<DauStatisticsDto> response = statisticsService.listDauStatistics(request);
		assertThat(response.getList().size()).isEqualTo(5);
	}

	@Test
	public void listDayStatisticsFailedWhenNoPermission() {
		assertException(PermissionDenyException.class, () -> {
			statisticsService.listDauStatistics(new ListDauStatisticsRequest());
		});
	}
}
