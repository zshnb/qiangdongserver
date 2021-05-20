package com.qiangdong.reader.service;

import com.qiangdong.reader.entity.Reward;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.user.RewardComicRequest;
import com.qiangdong.reader.request.user.RewardNovelRequest;
import com.qiangdong.reader.response.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
public interface IRewardService extends IService<Reward> {

	@Transactional(rollbackFor = RuntimeException.class)
	Response<Integer> rewardNovel(RewardNovelRequest request);

	@Transactional(rollbackFor = RuntimeException.class)
	Response<Integer> rewardComic(RewardComicRequest request);
}
