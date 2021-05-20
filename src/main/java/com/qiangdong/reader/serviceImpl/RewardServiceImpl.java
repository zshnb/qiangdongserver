package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.common.ComicConstant;
import com.qiangdong.reader.common.NovelConstant;
import com.qiangdong.reader.dao.ComicMapper;
import com.qiangdong.reader.dao.NovelMapper;
import com.qiangdong.reader.dao.RewardMapper;
import com.qiangdong.reader.dao.UserConsumptionMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.Reward;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserConsumption;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.enums.user_consumption.ConsumptionTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.RewardComicRequest;
import com.qiangdong.reader.request.user.RewardNovelRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IRewardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@Service
public class RewardServiceImpl extends ServiceImpl<RewardMapper, Reward> implements IRewardService {
	private final NovelMapper novelMapper;
	private final UserMapper userMapper;
	private final UserConsumptionMapper userConsumptionMapper;
	private final ComicMapper comicMapper;

	public RewardServiceImpl(NovelMapper novelMapper, UserMapper userMapper,
	                         UserConsumptionMapper userConsumptionMapper,
	                         ComicMapper comicMapper) {
		this.novelMapper = novelMapper;
		this.userMapper = userMapper;
		this.userConsumptionMapper = userConsumptionMapper;
		this.comicMapper = comicMapper;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<Integer> rewardNovel(RewardNovelRequest request) {
		Novel novel = novelMapper.selectById(request.getNovelId());
		if (novel == null) {
			throw new InvalidArgumentException("小说不存在");
		}
		User user = userMapper.selectById(request.getUserId());
		if (user.getCoin() < request.getCoin()) {
			throw new InvalidArgumentException("墙币余额不足");
		}

		novel.setCoin(novel.getCoin() + request.getCoin());
		if (request.getCoin() > NovelConstant.COIN_TO_TICKET_THRESHOLD) {
			novel.setWallTicket(novel.getWallTicket() + 1);
		}
		novelMapper.updateById(novel);

		user.setCoin(user.getCoin() - request.getCoin());
		userMapper.updateById(user);

		Reward reward = new Reward();
		reward.setUserId(request.getUserId());
		reward.setCount(request.getCoin());
		reward.setWorksId(request.getNovelId());
		reward.setWorksType(WorksTypeEnum.NOVEL);
		save(reward);

		UserConsumption userConsumption = new UserConsumption();
		userConsumption.setDescription("打赏");
		userConsumption.setUserId(request.getUserId());
		userConsumption.setAssociateId(novel.getId());
		userConsumption.setCount(request.getCoin());
		userConsumption.setType(ConsumptionTypeEnum.WALL_COIN);
		userConsumptionMapper.insert(userConsumption);

		return Response.ok(user.getCoin());
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<Integer> rewardComic(RewardComicRequest request) {
		Comic comic = comicMapper.selectById(request.getComicId());
		if (comic == null) {
			throw new InvalidArgumentException("漫画不存在");
		}
		User user = userMapper.selectById(request.getUserId());
		if (user.getCoin() < request.getCoin()) {
			throw new InvalidArgumentException("墙币余额不足");
		}

		comic.setCoin(comic.getCoin() + request.getCoin());
		if (request.getCoin() > ComicConstant.COIN_TO_TICKET_THRESHOLD) {
			comic.setWallTicket(comic.getWallTicket() + 1);
		}
		comicMapper.updateById(comic);

		user.setCoin(user.getCoin() - request.getCoin());
		userMapper.updateById(user);

		Reward reward = new Reward();
		reward.setWorksId(request.getComicId());
		reward.setUserId(request.getUserId());
		reward.setCount(request.getCoin());
		reward.setWorksType(WorksTypeEnum.COMIC);
		save(reward);

		UserConsumption userConsumption = new UserConsumption();
		userConsumption.setDescription("打赏");
		userConsumption.setUserId(request.getUserId());
		userConsumption.setAssociateId(comic.getId());
		userConsumption.setCount(request.getCoin());
		userConsumption.setType(ConsumptionTypeEnum.WALL_COIN);
		userConsumptionMapper.insert(userConsumption);
		return Response.ok(user.getCoin());
	}
}
