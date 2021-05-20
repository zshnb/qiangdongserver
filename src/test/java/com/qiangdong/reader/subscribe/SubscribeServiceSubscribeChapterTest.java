package com.qiangdong.reader.subscribe;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.common.NovelConstant;
import com.qiangdong.reader.dto.WorksRankingDto;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.subscribe.SubscribeChapterRequest;
import com.qiangdong.reader.schedule.WorksRankingSchedule;
import com.qiangdong.reader.serviceImpl.SubscribeServiceImpl;
import com.qiangdong.reader.utils.DateUtil;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

public class SubscribeServiceSubscribeChapterTest extends BaseTest {
	@Autowired
	private SubscribeServiceImpl subscribeService;

	@Autowired
	private RedisTemplate<String, WorksRankingDto> redisTemplate;

	@Autowired
	private WorksRankingSchedule worksRankingSchedule;

	@SpyBean
	private DateUtil dateUtil;

	@Test
	public void subscribeChapterSuccessful() {
		Mockito.when(dateUtil.getYesterday()).thenReturn(LocalDateTime.now());
		SubscribeChapterRequest request = new SubscribeChapterRequest();
		request.setUserId(userId);
		request.setWorksId(1L);
		request.setChapterId(1L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		subscribeService.subscribeChapter(request, new User());

		worksRankingSchedule.calculateSubscribeRanking();
		String key = String.format("%s-1", NovelConstant.KEY_TOP_SUBSCRIBE_NOVEL);
		ZSetOperations<String, WorksRankingDto> zSetOperations = redisTemplate.opsForZSet();
		Set<WorksRankingDto> worksRankingDtos = zSetOperations.reverseRangeByScore(key, 0, 100);
		assertThat(worksRankingDtos.size()).isEqualTo(1);
		assertThat(zSetOperations.score(key, worksRankingDtos.iterator().next())).isEqualTo(10.0);
	}

	@Test
	public void subscribeChapterFailedWhenChapterNotExist() {
		SubscribeChapterRequest request = new SubscribeChapterRequest();
		request.setUserId(1L);
		request.setWorksId(1L);
		request.setChapterId(-1L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		assertException(InvalidArgumentException.class, () -> subscribeService.subscribeChapter(request, new User()));
	}

	@Test
	public void subscribeChapterFailedWhenCoinNotEnough() {
		SubscribeChapterRequest request = new SubscribeChapterRequest();
		request.setUserId(3L);
		request.setWorksId(1L);
		request.setChapterId(-1L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		assertException(InvalidArgumentException.class, () -> subscribeService.subscribeChapter(request, new User()));
	}

	@Test
	public void subscribeChapterFailedWhenSubscribeTwice() {
		SubscribeChapterRequest request = new SubscribeChapterRequest();
		request.setUserId(userId);
		request.setWorksId(1L);
		request.setChapterId(1L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		subscribeService.subscribeChapter(request, new User());
		assertException(InvalidArgumentException.class, () -> subscribeService.subscribeChapter(request, new User()));
	}
}
