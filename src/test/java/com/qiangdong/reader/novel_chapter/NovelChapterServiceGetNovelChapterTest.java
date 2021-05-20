package com.qiangdong.reader.novel_chapter;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.NovelChapter;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.novel.GetNovelChapterRequest;
import com.qiangdong.reader.request.subscribe.SubscribeChapterRequest;
import com.qiangdong.reader.serviceImpl.NovelChapterServiceImpl;
import com.qiangdong.reader.serviceImpl.SubscribeServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelChapterServiceGetNovelChapterTest extends BaseTest {
	@Autowired
	private NovelChapterServiceImpl novelChapterService;

	@Autowired
	private SubscribeServiceImpl subscribeService;

	@Test
	public void getNovelChapterSuccessful() {
		SubscribeChapterRequest subscribeChapterRequest = new SubscribeChapterRequest();
		subscribeChapterRequest.setUserId(userId);
		subscribeChapterRequest.setWorksId(1L);
		subscribeChapterRequest.setChapterId(1L);
		subscribeChapterRequest.setWorksType(WorksTypeEnum.NOVEL);
		subscribeService.subscribeChapter(subscribeChapterRequest, new User());

		GetNovelChapterRequest request = new GetNovelChapterRequest();
		request.setNovelId(1L);
		request.setChapterId(1L);
		request.setIndex(1);
		request.setUserId(userId);
		NovelChapter novelChapter = novelChapterService.getNovelChapter(request);
		Assert.assertEquals(1L, novelChapter.getId().longValue());
		Assert.assertEquals("novel chapter 1", novelChapter.getTitle());
	}

	@Test
	public void getChapterFailedWhenNotSubscribe() {
		GetNovelChapterRequest request = new GetNovelChapterRequest();
		request.setNovelId(1L);
		request.setChapterId(1L);
		assertException(InvalidArgumentException.class, () -> novelChapterService.getNovelChapter(request));
	}


	@Test
	public void getChapterFailedWhenChapterIdIsInvalid() {
		GetNovelChapterRequest request = new GetNovelChapterRequest();
		request.setNovelId(1L);
		request.setChapterId(-1L);
		assertException(InvalidArgumentException.class, () -> novelChapterService.getNovelChapter(request));
	}
}
