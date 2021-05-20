//package com.qiangdong.reader.novel;
//
//import cn.hutool.core.date.DateTime;
//import cn.hutool.core.date.DateUtil;
//import com.qiangdong.reader.BaseTest;
//import com.qiangdong.reader.dto.WorksRankingDto;
//import com.qiangdong.reader.entity.User;
//import com.qiangdong.reader.enums.common.WorksTypeEnum;
//import com.qiangdong.reader.request.novel.ListRankingNovelRequest;
//import com.qiangdong.reader.request.subscribe.SubscribeChapterRequest;
//import com.qiangdong.reader.response.PageResponse;
//import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
//import com.qiangdong.reader.serviceImpl.SubscribeServiceImpl;
//import org.junit.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class NovelServiceListSellRankingTest extends BaseTest {
//	@Autowired
//	private SubscribeServiceImpl subscribeService;
//
//	@Autowired
//	private NovelServiceImpl novelService;
//
//	@Test
//	public void listSuccessful() {
//		Mockito.when(DateUtil.yesterday()).thenReturn(DateTime.now());
//		SubscribeChapterRequest request = new SubscribeChapterRequest();
//		request.setUserId(1L);
//		request.setWorksId(1L);
//		request.setChapterId(1L);
//		request.setWorksType(WorksTypeEnum.NOVEL);
//		subscribeService.subscribeChapter(request, new User());
//		request.setChapterId(2L);
//		subscribeService.subscribeChapter(request, new User());
//
//		ListRankingNovelRequest listRankingNovelRequest = new ListRankingNovelRequest();
//		listRankingNovelRequest.setTypeId(1L);
//		PageResponse<WorksRankingDto> response = novelService.listSellRankingNovel(listRankingNovelRequest);
//		assertThat(response.getList().size()).isEqualTo(1);
//	}
//}
// TODO: 2020/7/24 need static mock 
