package com.qiangdong.reader.schedule;

import com.qiangdong.reader.common.ComicConstant;
import com.qiangdong.reader.common.NovelConstant;
import com.qiangdong.reader.dao.BookStandMapper;
import com.qiangdong.reader.dao.ComicChapterMapper;
import com.qiangdong.reader.dao.ComicMapper;
import com.qiangdong.reader.dao.NovelChapterMapper;
import com.qiangdong.reader.dao.NovelMapper;
import com.qiangdong.reader.dao.RewardMapper;
import com.qiangdong.reader.dao.SubscribeMapper;
import com.qiangdong.reader.dao.TypeMapper;
import com.qiangdong.reader.dao.UserConsumptionMapper;
import com.qiangdong.reader.dto.WorksRankingDto;
import com.qiangdong.reader.dto.comic.ComicDto;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.entity.BookStand;
import com.qiangdong.reader.entity.ComicChapter;
import com.qiangdong.reader.entity.NovelChapter;
import com.qiangdong.reader.entity.Reward;
import com.qiangdong.reader.entity.Subscribe;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.UserConsumption;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.enums.user_consumption.ConsumptionTypeEnum;
import com.qiangdong.reader.utils.DateUtil;
import com.qiangdong.reader.utils.TypeUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 作品榜单定时计算任务
 * */
@Component
public class WorksRankingSchedule {
    private final Logger logger = LoggerFactory.getLogger(WorksRankingSchedule.class);
    private final SubscribeMapper subscribeMapper;
    private final RewardMapper rewardMapper;
    private final NovelMapper novelMapper;
    private final ComicMapper comicMapper;
    private final TypeMapper typeMapper;
    private final NovelChapterMapper novelChapterMapper;
    private final ComicChapterMapper comicChapterMapper;
    private final BookStandMapper bookStandMapper;
    private final UserConsumptionMapper userConsumptionMapper;
    private final RedisTemplate<String, WorksRankingDto> redisTemplate;
    private final DateUtil dateUtil;
    private final TypeUtil typeUtil;

    public WorksRankingSchedule(SubscribeMapper subscribeMapper,
                                RewardMapper rewardMapper, NovelMapper novelMapper,
                                ComicMapper comicMapper,
                                TypeMapper typeMapper,
                                NovelChapterMapper novelChapterMapper,
                                ComicChapterMapper comicChapterMapper,
                                BookStandMapper bookStandMapper,
                                UserConsumptionMapper userConsumptionMapper,
                                RedisTemplate<String, WorksRankingDto> redisTemplate,
                                DateUtil dateUtil, TypeUtil typeUtil) {
        this.subscribeMapper = subscribeMapper;
        this.rewardMapper = rewardMapper;
        this.novelMapper = novelMapper;
        this.comicMapper = comicMapper;
        this.typeMapper = typeMapper;
        this.novelChapterMapper = novelChapterMapper;
        this.comicChapterMapper = comicChapterMapper;
        this.bookStandMapper = bookStandMapper;
        this.userConsumptionMapper = userConsumptionMapper;
        this.redisTemplate = redisTemplate;
        this.dateUtil = dateUtil;
        this.typeUtil = typeUtil;
    }

    /**
     * 每天凌晨计算昨天订阅的榜单
     * */
    @Scheduled(cron = "1 0 0 * * ?")
    public void calculateSubscribeRanking() {
        logger.info("start calculate subscribe ranking");
        redisTemplate.delete(String.format("%s-1", NovelConstant.KEY_TOP_SUBSCRIBE_NOVEL));
        redisTemplate.delete(String.format("%s-2", NovelConstant.KEY_TOP_SUBSCRIBE_NOVEL));
        redisTemplate.delete(ComicConstant.KEY_TOP_SUBSCRIBE_COMIC);
        LocalDateTime yesterday = dateUtil.getYesterday();
        Map<Long, List<Subscribe>> novelSubscribes =
            subscribeMapper.findYesterdaySubscribeByWorksType(yesterday, WorksTypeEnum.NOVEL).stream()
            .collect(Collectors.groupingBy(Subscribe::getWorksId));
        ZSetOperations<String, WorksRankingDto> novelRankingZSetOperation = redisTemplate.opsForZSet();
        novelSubscribes.forEach((id, subscribes) -> {
            NovelDto novelDto = novelMapper.findByNovelId(id);
            WorksRankingDto worksRankingDto = new WorksRankingDto();
            worksRankingDto.setWorksId(id);
            BeanUtils.copyProperties(novelDto, worksRankingDto);
            double count = subscribes.stream()
                .mapToDouble(Subscribe::getCoin)
                .sum();
            Type rootType = typeUtil.getNovelRootType(typeMapper.selectById(novelDto.getTypeId()));
            String key = String.format("%s-%s", NovelConstant.KEY_TOP_SUBSCRIBE_NOVEL, rootType.getId());
            novelRankingZSetOperation.add(key, worksRankingDto, count);
        });

        Map<Long, List<Subscribe>> comicSubscribes =
            subscribeMapper.findYesterdaySubscribeByWorksType(yesterday, WorksTypeEnum.COMIC).stream()
                .collect(Collectors.groupingBy(Subscribe::getWorksId));
        ZSetOperations<String, WorksRankingDto> comicRankingZSetOperation = redisTemplate.opsForZSet();
        comicSubscribes.forEach((id, subscribes) -> {
            ComicDto comicDto = comicMapper.findByComicId(id);
            WorksRankingDto worksRankingDto = new WorksRankingDto();
            worksRankingDto.setWorksId(id);
            BeanUtils.copyProperties(comicDto, worksRankingDto);
            double count = subscribes.stream()
                .mapToDouble(Subscribe::getCoin)
                .sum();
            comicRankingZSetOperation.add(ComicConstant.KEY_TOP_SUBSCRIBE_COMIC, worksRankingDto, count);
        });

        logger.info("end calculate subscribe ranking");
    }

    /**
     * 每天凌晨计算昨天打赏的榜单
     * */
    @Scheduled(cron = "1 0 0 * * ?")
    public void calculateRewardRanking() {
        logger.info("start calculate reward ranking");
        redisTemplate.delete(String.format("%s-1", NovelConstant.KEY_TOP_REWARD_NOVEL));
        redisTemplate.delete(String.format("%s-2", NovelConstant.KEY_TOP_REWARD_NOVEL));
        redisTemplate.delete(ComicConstant.KEY_TOP_REWARD_COMIC);
        LocalDateTime yesterday = dateUtil.getYesterday();
        Map<Long, List<Reward>> novelRewards =
            rewardMapper.findYesterdayRewardByWorksType(yesterday, WorksTypeEnum.NOVEL).stream()
                .collect(Collectors.groupingBy(Reward::getWorksId));
        ZSetOperations<String, WorksRankingDto> novelRankingZSetOperation = redisTemplate.opsForZSet();
        novelRewards.forEach((id, rewards) -> {
            NovelDto novelDto = novelMapper.findByNovelId(id);
            WorksRankingDto worksRankingDto = new WorksRankingDto();
            worksRankingDto.setWorksId(id);
            BeanUtils.copyProperties(novelDto, worksRankingDto);
            int count = rewards.stream()
                .mapToInt(Reward::getCount)
                .sum();
            Type rootType = typeUtil.getNovelRootType(typeMapper.selectById(novelDto.getTypeId()));
            String key = String.format("%s-%s", NovelConstant.KEY_TOP_REWARD_NOVEL, rootType.getId());
            novelRankingZSetOperation.add(key, worksRankingDto, count);
        });

        Map<Long, List<Reward>> comicRewards =
            rewardMapper.findYesterdayRewardByWorksType(yesterday, WorksTypeEnum.COMIC).stream()
                .collect(Collectors.groupingBy(Reward::getWorksId));
        ZSetOperations<String, WorksRankingDto> comicRankingZSetOperation = redisTemplate.opsForZSet();
        comicRewards.forEach((id, rewards) -> {
            ComicDto comicDto = comicMapper.findByComicId(id);
            WorksRankingDto worksRankingDto = new WorksRankingDto();
            worksRankingDto.setWorksId(id);
            BeanUtils.copyProperties(comicDto, worksRankingDto);
            int count = rewards.stream()
                .mapToInt(Reward::getCount)
                .sum();
            comicRankingZSetOperation.add(ComicConstant.KEY_TOP_REWARD_COMIC, worksRankingDto, count);
        });

        logger.info("end calculate reward ranking");
    }

    /**
     * 每天凌晨计算昨天推荐的榜单
     * */
    @Scheduled(cron = "1 0 0 * * ?")
    public void calculateRecommendRanking() {
        logger.info("start calculate recommend ranking");
        redisTemplate.delete(String.format("%s-1", NovelConstant.KEY_TOP_RECOMMEND_NOVEL));
        redisTemplate.delete(String.format("%s-2", NovelConstant.KEY_TOP_RECOMMEND_NOVEL));
        redisTemplate.delete(ComicConstant.KEY_TOP_RECOMMEND_COMIC);
        LocalDateTime yesterday = dateUtil.getYesterday();
        Map<Long, List<UserConsumption>> novelRecommends =
            userConsumptionMapper.findYesterdayNovelRecommendTicket(yesterday, ConsumptionTypeEnum.RECOMMEND_TICKET)
                .stream()
                .collect(Collectors.groupingBy(UserConsumption::getAssociateId));
        ZSetOperations<String, WorksRankingDto> novelRankingZSetOperation = redisTemplate.opsForZSet();
        novelRecommends.forEach((id, recommends) -> {
            NovelDto novelDto = novelMapper.findByNovelId(id);
            WorksRankingDto worksRankingDto = new WorksRankingDto();
            worksRankingDto.setWorksId(id);
            BeanUtils.copyProperties(novelDto, worksRankingDto);
            int count = recommends.stream()
                .mapToInt(UserConsumption::getCount)
                .sum();
            Type rootType = typeUtil.getNovelRootType(typeMapper.selectById(novelDto.getTypeId()));
            String key = String.format("%s-%s", NovelConstant.KEY_TOP_RECOMMEND_NOVEL, rootType.getId());
            novelRankingZSetOperation.add(key, worksRankingDto, count);
        });

        Map<Long, List<UserConsumption>> comicRecommends =
            userConsumptionMapper.findYesterdayComicRecommendTicket(yesterday, ConsumptionTypeEnum.RECOMMEND_TICKET)
                .stream()
                .collect(Collectors.groupingBy(UserConsumption::getAssociateId));
        ZSetOperations<String, WorksRankingDto> comicRankingZSetOperation = redisTemplate.opsForZSet();
        comicRecommends.forEach((id, recommends) -> {
            ComicDto comicDto = comicMapper.findByComicId(id);
            WorksRankingDto worksRankingDto = new WorksRankingDto();
            worksRankingDto.setWorksId(id);
            BeanUtils.copyProperties(comicDto, worksRankingDto);
            int count = recommends.stream()
                .mapToInt(UserConsumption::getCount)
                .sum();
            comicRankingZSetOperation.add(ComicConstant.KEY_TOP_RECOMMEND_COMIC, worksRankingDto, count);
        });

        logger.info("end calculate recommend ranking");
    }

    /**
     * 每天凌晨计算昨天更新的榜单
     * */
    @Scheduled(cron = "1 0 0 * * ?")
    public void calculateUpdateRanking() {
        logger.info("start calculate update ranking");
        redisTemplate.delete(String.format("%s-1", NovelConstant.KEY_TOP_UPDATE_NOVEL));
        redisTemplate.delete(String.format("%s-2", NovelConstant.KEY_TOP_UPDATE_NOVEL));
        redisTemplate.delete(ComicConstant.KEY_TOP_UPDATE_COMIC);
        LocalDateTime yesterday = dateUtil.getYesterday();
        Map<Long, List<NovelChapter>> novelChapters =
            novelChapterMapper.findYesterdayUpdate(yesterday)
                .stream()
                .collect(Collectors.groupingBy(NovelChapter::getNovelId));
        ZSetOperations<String, WorksRankingDto> novelRankingZSetOperation = redisTemplate.opsForZSet();
        novelChapters.forEach((id, chapters) -> {
            NovelDto novelDto = novelMapper.findByNovelId(id);
            WorksRankingDto worksRankingDto = new WorksRankingDto();
            worksRankingDto.setWorksId(id);
            BeanUtils.copyProperties(novelDto, worksRankingDto);
            int count = chapters.stream()
                .mapToInt(NovelChapter::getWordCount)
                .sum();
            Type rootType = typeUtil.getNovelRootType(typeMapper.selectById(novelDto.getTypeId()));
            String key = String.format("%s-%s", NovelConstant.KEY_TOP_UPDATE_NOVEL, rootType.getId());
            novelRankingZSetOperation.add(key, worksRankingDto, count);
        });

        Map<Long, List<ComicChapter>> comicChapters =
            comicChapterMapper.findYesterdayUpdate(yesterday)
                .stream()
                .collect(Collectors.groupingBy(ComicChapter::getComicId));
        ZSetOperations<String, WorksRankingDto> comicRankingZSetOperation = redisTemplate.opsForZSet();
        comicChapters.forEach((id, chapters) -> {
            ComicDto comicDto = comicMapper.findByComicId(id);
            WorksRankingDto worksRankingDto = new WorksRankingDto();
            worksRankingDto.setWorksId(id);
            BeanUtils.copyProperties(comicDto, worksRankingDto);
            int count = chapters.stream()
                .mapToInt(ComicChapter::getPictureCount)
                .sum();
            comicRankingZSetOperation.add(ComicConstant.KEY_TOP_UPDATE_COMIC, worksRankingDto, count);
        });

        logger.info("end calculate update ranking");
    }

    /**
     * 每天凌晨计算昨天收藏的榜单
     * */
    @Scheduled(cron = "1 0 0 * * ?")
    public void calculateCollectionRanking() {
        logger.info("start calculate collection ranking");
        redisTemplate.delete(String.format("%s-1", NovelConstant.KEY_TOP_COLLECTION_NOVEL));
        redisTemplate.delete(String.format("%s-2", NovelConstant.KEY_TOP_COLLECTION_NOVEL));
        redisTemplate.delete(ComicConstant.KEY_TOP_COLLECTION_COMIC);
        LocalDateTime yesterday = dateUtil.getYesterday();
        Map<Long, List<BookStand>> novelCollection =
            bookStandMapper.findYesterdayAddBookStand(yesterday, WorksTypeEnum.NOVEL)
                .stream()
                .collect(Collectors.groupingBy(BookStand::getAssociateId));
        ZSetOperations<String, WorksRankingDto> novelRankingZSetOperation = redisTemplate.opsForZSet();
        novelCollection.forEach((id, collections) -> {
            NovelDto novelDto = novelMapper.findByNovelId(id);
            WorksRankingDto worksRankingDto = new WorksRankingDto();
            worksRankingDto.setWorksId(id);
            BeanUtils.copyProperties(novelDto, worksRankingDto);
            Type rootType = typeUtil.getNovelRootType(typeMapper.selectById(novelDto.getTypeId()));
            String key = String.format("%s-%s", NovelConstant.KEY_TOP_UPDATE_NOVEL, rootType.getId());
            novelRankingZSetOperation.add(key, worksRankingDto, collections.size());
        });

        Map<Long, List<BookStand>> comicCollection =
            bookStandMapper.findYesterdayAddBookStand(yesterday, WorksTypeEnum.COMIC)
                .stream()
                .collect(Collectors.groupingBy(BookStand::getAssociateId));
        ZSetOperations<String, WorksRankingDto> comicRankingZSetOperation = redisTemplate.opsForZSet();
        comicCollection.forEach((id, collections) -> {
            ComicDto comicDto = comicMapper.findByComicId(id);
            WorksRankingDto worksRankingDto = new WorksRankingDto();
            worksRankingDto.setWorksId(id);
            BeanUtils.copyProperties(comicDto, worksRankingDto);
            comicRankingZSetOperation.add(ComicConstant.KEY_TOP_UPDATE_COMIC, worksRankingDto, collections.size());
        });

        logger.info("end calculate collection ranking");
    }
}
