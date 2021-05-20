package com.qiangdong.reader.reward;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.common.NovelConstant;
import com.qiangdong.reader.dto.WorksRankingDto;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserConsumption;
import com.qiangdong.reader.enums.user_consumption.ConsumptionTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.RewardNovelRequest;
import com.qiangdong.reader.schedule.WorksRankingSchedule;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import com.qiangdong.reader.serviceImpl.RewardServiceImpl;
import com.qiangdong.reader.serviceImpl.UserConsumptionServiceImpl;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import com.qiangdong.reader.utils.DateUtil;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

public class RewardServiceRewardNovelTest extends BaseTest {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RewardServiceImpl rewardService;

    @Autowired
    private NovelServiceImpl novelService;

    @Autowired
    private RedisTemplate<String, WorksRankingDto> redisTemplate;

    @Autowired
    private UserConsumptionServiceImpl userConsumptionService;

    @Autowired
    private WorksRankingSchedule worksRankingSchedule;

    @SpyBean
    private DateUtil dateUtil;

    @Test
    public void rewardNovelSuccessful() {
        Mockito.when(dateUtil.getYesterday()).thenReturn(LocalDateTime.now());
        RewardNovelRequest request = new RewardNovelRequest();
        request.setUserId(1L);
        request.setNovelId(1L);
        request.setCoin(3000);
        rewardService.rewardNovel(request);

        NovelDto novel = novelService.getNovel(getNovelRequest, new Novel()).getNovel();
        Assert.assertEquals(3000, novel.getCoin().intValue());
        Assert.assertEquals(0, novel.getWallTicket().intValue());

        User user = userService.getById(request.getUserId());
        Assert.assertEquals(17000, user.getCoin().intValue());


        UserConsumption userConsumption = userConsumptionService.getOne(new QueryWrapper<UserConsumption>()
            .eq("user_id", authorUserId)
            .eq("associate_id", 1L)
            .eq("type", ConsumptionTypeEnum.WALL_COIN));
        assertThat(userConsumption.getId()).isNotZero();
        assertThat(userConsumption.getCount()).isEqualTo(3000);

        worksRankingSchedule.calculateRewardRanking();
        String key = String.format("%s-1", NovelConstant.KEY_TOP_REWARD_NOVEL);
        ZSetOperations<String, WorksRankingDto> zSetOperations = redisTemplate.opsForZSet();
        Set<WorksRankingDto> worksRankingDtos = zSetOperations.reverseRangeByScore(key, 0, 5000);
        assertThat(worksRankingDtos.size()).isEqualTo(1);
        assertThat(zSetOperations.score(key, worksRankingDtos.iterator().next())).isEqualTo(3000.0);
    }

    @Test
    public void rewardLargeThanThreshold() {
        RewardNovelRequest request = new RewardNovelRequest();
        request.setUserId(1L);
        request.setNovelId(1L);
        request.setCoin(13000);
        rewardService.rewardNovel(request);

        NovelDto novel = novelService.getNovel(getNovelRequest, new Novel()).getNovel();
        Assert.assertEquals(13000, novel.getCoin().intValue());
        Assert.assertEquals(1, novel.getWallTicket().intValue());

        User user = userService.getById(request.getUserId());
        Assert.assertEquals(7000, user.getCoin().intValue());
    }

    @Test
    public void rewardFailedWhenNovelIdIsInvalid() {
        RewardNovelRequest request = new RewardNovelRequest();
        request.setUserId(1L);
        request.setNovelId(-1L);
        request.setCoin(13000);
        assertException(InvalidArgumentException.class, () -> rewardService.rewardNovel(request));
    }

    @Test
    public void rewardFailedWhenExceedBalance() {
        RewardNovelRequest request = new RewardNovelRequest();
        request.setUserId(1L);
        request.setNovelId(1L);
        request.setCoin(23000);
        assertException(InvalidArgumentException.class, () -> rewardService.rewardNovel(request));
    }
}
