//package com.qiangdong.reader.novel;
//
//import com.qiangdong.reader.BaseTest;
//import com.qiangdong.reader.request.novel.GetNovelRewardRankingRequest;
//import com.qiangdong.reader.request.user.RewardNovelRequest;
//import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
//import com.qiangdong.reader.serviceImpl.UserServiceImpl;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class NovelServiceGetNovelRewardRankingTest extends BaseTest {
//	@Autowired
//	private NovelServiceImpl novelService;
//
//	@Autowired
//	private UserServiceImpl userService;
//
//	@Test
//	public void getNovelRewardRankingSuccessful() {
//		RewardNovelRequest rewardNovelRequest = new RewardNovelRequest();
//		rewardNovelRequest.setUserId(1L);
//		rewardNovelRequest.setNovelId(1L);
//		rewardNovelRequest.setCoin(1);
//		userService.rewardNovel(rewardNovelRequest);
//
//		GetNovelRewardRankingRequest request = new GetNovelRewardRankingRequest();
//		request.setNovelId(1L);
//		long ranking = novelService.getNovelRewardRanking(request).getData();
//		assertThat(ranking).isEqualTo(1L);
//	}
//}
// TODO: 2020/7/24 need static mock
