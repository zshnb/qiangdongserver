package com.qiangdong.reader.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserConsumption;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.enums.user_consumption.ConsumptionTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.VoteTicketRequest;
import com.qiangdong.reader.serviceImpl.UserConsumptionServiceImpl;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceVoteWallTicketTest extends BaseTest {
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private UserConsumptionServiceImpl userConsumptionService;

	@Test
	public void voteWallTicketNovelSuccessful() {
		VoteTicketRequest request = new VoteTicketRequest();
		request.setUserId(1L);
		request.setWorksId(1L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		request.setCount(10);
		userService.voteWallTicket(request, new User());

		User user = userService.getById(authorUserId);
		assertThat(user.getWallTicket()).isZero();

		UserConsumption userConsumption = userConsumptionService.getOne(new QueryWrapper<UserConsumption>()
			.eq("user_id", authorUserId)
			.eq("associate_id", 1L)
			.eq("type", ConsumptionTypeEnum.WALL_TICKET));
		assertThat(userConsumption.getId()).isNotZero();
		assertThat(userConsumption.getCount()).isEqualTo(10);
	}

	@Test
	public void voteWallTicketNovelFailedWhenNovelNotExist() {
		VoteTicketRequest request = new VoteTicketRequest();
		request.setUserId(1L);
		request.setWorksId(-1L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		request.setCount(10);
		assertException(InvalidArgumentException.class, () -> userService.voteWallTicket(request, new User()));
	}

	@Test
	public void voteRecommendNovelFailedWhenTicketNotEnough() {
		VoteTicketRequest request = new VoteTicketRequest();
		request.setUserId(2L);
		request.setWorksId(1L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		request.setCount(100);
		assertException(InvalidArgumentException.class, () -> userService.voteWallTicket(request, new User()));
	}
}
