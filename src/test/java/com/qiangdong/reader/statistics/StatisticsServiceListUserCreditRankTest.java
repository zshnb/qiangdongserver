package com.qiangdong.reader.statistics;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.statistics.UserCreditRankDto;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.StatisticsServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StatisticsServiceListUserCreditRankTest extends BaseTest {
	@Autowired
	private StatisticsServiceImpl statisticsService;

	@Test
	public void listSuccessful() {
		BaseRequest request = new BaseRequest();
		request.setUserId(adminUserId);
		PageResponse<UserCreditRankDto> response = statisticsService.listUserCreditRank(request);
		assertThat(response.getList().size()).isEqualTo(3);
		assertThat(response.getList().get(0).getCount().intValue()).isEqualTo(50);
		assertThat(response.getList().get(0).getUsername()).isEqualTo("user2");
		assertThat(response.getList().get(1).getCount().intValue()).isEqualTo(20);
		assertThat(response.getList().get(1).getUsername()).isEqualTo("user1");
	}
}
