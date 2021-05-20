package com.qiangdong.reader.comic_chapter;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.ComicChapter;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comic.GetComicChapterRequest;
import com.qiangdong.reader.request.subscribe.SubscribeChapterRequest;
import com.qiangdong.reader.serviceImpl.ComicChapterServiceImpl;
import com.qiangdong.reader.serviceImpl.SubscribeServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicChapterServiceGetComicChapterTest extends BaseTest {
	@Autowired
	private ComicChapterServiceImpl comicChapterService;

	@Autowired
	private SubscribeServiceImpl subscribeService;

	@Test
	public void getComicChapterSuccessful() {
		SubscribeChapterRequest subscribeChapterRequest = new SubscribeChapterRequest();
		subscribeChapterRequest.setUserId(userId);
		subscribeChapterRequest.setWorksId(1L);
		subscribeChapterRequest.setChapterId(1L);
		subscribeChapterRequest.setWorksType(WorksTypeEnum.COMIC);
		subscribeService.subscribeChapter(subscribeChapterRequest, new User());

		GetComicChapterRequest request = new GetComicChapterRequest();
		request.setComicId(1L);
		request.setChapterId(1L);
		request.setIndex(1);
		request.setUserId(userId);
		ComicChapter comicChapter = comicChapterService.getComicChapter(request);
		Assert.assertEquals(1L, comicChapter.getId().longValue());
		Assert.assertEquals("comic chapter 1-1", comicChapter.getTitle());
	}

	@Test
	public void getChapterFailedWhenNotSubscribe() {
		GetComicChapterRequest request = new GetComicChapterRequest();
		request.setUserId(userId);
		request.setComicId(1L);
		request.setChapterId(1L);
		assertException(InvalidArgumentException.class, () -> comicChapterService.getComicChapter(request));
	}

	@Test
	public void getChapterFailedWhenChapterIdIsInvalid() {
		GetComicChapterRequest request = new GetComicChapterRequest();
		request.setComicId(1L);
		request.setChapterId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			comicChapterService.getComicChapter(request);
		});
	}
}
