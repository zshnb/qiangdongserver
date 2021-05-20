package com.qiangdong.reader.reward;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.common.ComicConstant;
import com.qiangdong.reader.dto.WorksRankingDto;
import com.qiangdong.reader.dto.comic.ComicDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserConsumption;
import com.qiangdong.reader.enums.user_consumption.ConsumptionTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.RewardComicRequest;
import com.qiangdong.reader.schedule.WorksRankingSchedule;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
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

public class RewardServiceRewardComicTest extends BaseTest {
    @Autowired
    private RewardServiceImpl rewardService;

    @Autowired
    private ComicServiceImpl comicService;

    @Autowired
    private UserConsumptionServiceImpl userConsumptionService;

    @Autowired
    private RedisTemplate<String, WorksRankingDto> redisTemplate;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private WorksRankingSchedule worksRankingSchedule;

    @SpyBean
    private DateUtil dateUtil;

    @Test
    public void rewardComicSuccessful() {
        Mockito.when(dateUtil.getYesterday()).thenReturn(LocalDateTime.now());
        RewardComicRequest request = new RewardComicRequest();
        request.setUserId(1L);
        request.setComicId(1L);
        request.setCoin(3000);
        rewardService.rewardComic(request);

        ComicDto comicDto = comicService.getComic(getComicRequest, new Comic()).getComic();
        Assert.assertEquals(3000, comicDto.getCoin().intValue());
        Assert.assertEquals(0, comicDto.getWallTicket().intValue());

        User user = userService.getById(request.getUserId());
        Assert.assertEquals(17000, user.getCoin().intValue());

        UserConsumption userConsumption = userConsumptionService.getOne(new QueryWrapper<UserConsumption>()
            .eq("user_id", authorUserId)
            .eq("associate_id", 1L)
            .eq("type", ConsumptionTypeEnum.WALL_COIN));
        assertThat(userConsumption.getId()).isNotZero();
        assertThat(userConsumption.getCount()).isEqualTo(3000);

        worksRankingSchedule.calculateRewardRanking();
        String key = ComicConstant.KEY_TOP_REWARD_COMIC;
        ZSetOperations<String, WorksRankingDto> zSetOperations = redisTemplate.opsForZSet();
        Set<WorksRankingDto> worksRankingDtos = zSetOperations.reverseRangeByScore(key, 0, 5000);
        assertThat(worksRankingDtos.size()).isEqualTo(1);
        assertThat(zSetOperations.score(key, worksRankingDtos.iterator().next())).isEqualTo(3000.0);
    }

    @Test
    public void rewardLargeThanThreshold() {
        RewardComicRequest request = new RewardComicRequest();
        request.setUserId(1L);
        request.setComicId(1L);
        request.setCoin(13000);
        rewardService.rewardComic(request);

        ComicDto comicDto = comicService.getComic(getComicRequest, new Comic()).getComic();
        Assert.assertEquals(13000, comicDto.getCoin().intValue());
        Assert.assertEquals(1, comicDto.getWallTicket().intValue());

        User user = userService.getById(request.getUserId());
        Assert.assertEquals(7000, user.getCoin().intValue());
    }

    @Test
    public void rewardFailedWhenComicIdIsInvalid() {
        RewardComicRequest request = new RewardComicRequest();
        request.setUserId(1L);
        request.setComicId(-1L);
        request.setCoin(13000);
        assertException(InvalidArgumentException.class, () -> {
            rewardService.rewardComic(request);
        });
    }

    @Test
    public void rewardFailedWhenExceedBalance() {
        RewardComicRequest request = new RewardComicRequest();
        request.setUserId(1L);
        request.setComicId(1L);
        request.setCoin(23000);
        assertException(InvalidArgumentException.class, () -> {
            rewardService.rewardComic(request);
        });
    }
}
