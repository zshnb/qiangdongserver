package com.qiangdong.reader.statistics;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.common.UserConstant;
import com.qiangdong.reader.dto.statistics.RegisterStatisticsDto;
import com.qiangdong.reader.dto.statistics.StatisticsDataOverviewDto;
import com.qiangdong.reader.enums.statistics.DateUnitEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.statistics.ListRegisterStatisticsRequest;
import com.qiangdong.reader.request.user.UserRegisterRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.StatisticsServiceImpl;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class StatisticsServiceListRegisterStatisticsTest extends BaseTest {
	@Autowired
	private StatisticsServiceImpl statisticsService;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;


	@Test
	public void listDayStatisticsSuccessful() {
		ListRegisterStatisticsRequest request = new ListRegisterStatisticsRequest();
		request.setUserId(adminUserId);
		request.setDateUnit(DateUnitEnum.DAY);
		PageResponse<RegisterStatisticsDto> response = statisticsService.listRegisterStatistics(request);
		assertThat(response.getList().size()).isEqualTo(30);
	}

	@Test
	public void listMonthStatisticsSuccessful() {
		ListRegisterStatisticsRequest request = new ListRegisterStatisticsRequest();
		request.setUserId(adminUserId);
		request.setDateUnit(DateUnitEnum.MONTH);
		PageResponse<RegisterStatisticsDto> response = statisticsService.listRegisterStatistics(request);
		assertThat(response.getList().size()).isEqualTo(12);
	}

	@Test
	public void listYearStatisticsSuccessful() {
		ListRegisterStatisticsRequest request = new ListRegisterStatisticsRequest();
		request.setUserId(adminUserId);
		request.setDateUnit(DateUnitEnum.YEAR);
		PageResponse<RegisterStatisticsDto> response = statisticsService.listRegisterStatistics(request);
		assertThat(response.getList().size()).isEqualTo(5);
	}

	@Test
	public void listDayStatisticsFailedWhenNoPermission() {
		assertException(PermissionDenyException.class, () -> {
			statisticsService.listRegisterStatistics(new ListRegisterStatisticsRequest());
		});
	}
}
