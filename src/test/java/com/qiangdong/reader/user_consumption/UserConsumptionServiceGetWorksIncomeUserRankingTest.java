package com.qiangdong.reader.user_consumption;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.WorksIncomeUserRankingDto;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.enums.user_consumption.ConsumptionTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.ListWorksIncomeUserRankingRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.UserConsumptionServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserConsumptionServiceGetWorksIncomeUserRankingTest extends BaseTest {
	@Autowired
	private UserConsumptionServiceImpl userConsumptionService;

	@Test
	public void getWorksIncomeUserRankingSuccessful() {
		ListWorksIncomeUserRankingRequest request = new ListWorksIncomeUserRankingRequest();
		request.setWorksId(1L);
		request.setConsumptionType(ConsumptionTypeEnum.RECOMMEND_TICKET);
		request.setWorksType(WorksTypeEnum.NOVEL);
		PageResponse<WorksIncomeUserRankingDto> response = userConsumptionService.listWorksWorksIncomeUserRanking(request);
		assertThat(response.getList().size()).isEqualTo(2);
		assertThat(response.getList().get(0).getSum()).isEqualTo(20);
		assertThat(response.getList().get(1).getSum()).isEqualTo(10);
	}

	@Test
	public void getWorksIncomeUserRankingFailedWhenNovelNotExist() {
		ListWorksIncomeUserRankingRequest request = new ListWorksIncomeUserRankingRequest();
		request.setWorksId(-1L);
		request.setConsumptionType(ConsumptionTypeEnum.RECOMMEND_TICKET);
		request.setWorksType(WorksTypeEnum.NOVEL);
		assertException(InvalidArgumentException.class, () -> {
			userConsumptionService.listWorksWorksIncomeUserRanking(request);
		});
	}
}
