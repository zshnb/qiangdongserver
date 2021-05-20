package com.qiangdong.reader.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.common.NovelConstant;
import com.qiangdong.reader.dto.WorksRankingDto;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserConsumption;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.enums.user_consumption.ConsumptionTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.VoteTicketRequest;
import com.qiangdong.reader.schedule.WorksRankingSchedule;
import com.qiangdong.reader.serviceImpl.UserConsumptionServiceImpl;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import com.qiangdong.reader.utils.DateUtil;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

public class UserServiceVoteRecommendTicketTest extends BaseTest {
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private RedisTemplate<String, WorksRankingDto> redisTemplate;

	@Autowired
	private UserConsumptionServiceImpl userConsumptionService;

	@Autowired
	private WorksRankingSchedule worksRankingSchedule;

	@SpyBean
	private DateUtil dateUtil;

	@Test
	public void voteRecommendNovelSuccessful() {
		Mockito.when(dateUtil.getYesterday()).thenReturn(LocalDateTime.now());
		VoteTicketRequest request = new VoteTicketRequest();
		request.setUserId(1L);
		request.setWorksId(1L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		request.setCount(10);
		userService.voteRecommendTicket(request, new User());

		UserConsumption userConsumption = userConsumptionService.getOne(new QueryWrapper<UserConsumption>()
			.eq("user_id", authorUserId)
			.eq("associate_id", 1L)
			.eq("type", ConsumptionTypeEnum.RECOMMEND_TICKET)
			.ne("id", 1L));
		assertThat(userConsumption.getId()).isNotZero();
		assertThat(userConsumption.getCount()).isEqualTo(10);

		worksRankingSchedule.calculateRecommendRanking();
		ZSetOperations<String, WorksRankingDto> zSetOperations = redisTemplate.opsForZSet();
		String key = String.format("%s-1", NovelConstant.KEY_TOP_RECOMMEND_NOVEL);
		Set<WorksRankingDto> worksRankingDtos = zSetOperations.reverseRangeByScore(key, 0, 10);
		assertThat(worksRankingDtos.size()).isEqualTo(1);
		assertThat(zSetOperations.score(key, worksRankingDtos.iterator().next())).isEqualTo(10.0);
	}

	@Test
	public void voteRecommendNovelFailedWhenNovelNotExist() {
		VoteTicketRequest request = new VoteTicketRequest();
		request.setUserId(1L);
		request.setWorksId(-1L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		request.setCount(10);
		assertException(InvalidArgumentException.class, () -> userService.voteRecommendTicket(request, new User()));
	}

	@Test
	public void voteRecommendNovelFailedWhenTicketNotEnough() {
		VoteTicketRequest request = new VoteTicketRequest();
		request.setUserId(2L);
		request.setWorksId(1L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		request.setCount(10);
		assertException(InvalidArgumentException.class, () -> userService.voteRecommendTicket(request, new User()));
	}
}
