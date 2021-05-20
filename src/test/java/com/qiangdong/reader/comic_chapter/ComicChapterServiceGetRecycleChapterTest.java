package com.qiangdong.reader.comic_chapter;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.comic.ComicChapterDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.comic.DeleteComicChapterRequest;
import com.qiangdong.reader.request.comic.GetRecycleComicChapterRequest;
import com.qiangdong.reader.serviceImpl.ComicChapterServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicChapterServiceGetRecycleChapterTest extends BaseTest {
	@Autowired
	private ComicChapterServiceImpl comicChapterService;

	@Test
	public void getChapterSuccessful() {
		DeleteComicChapterRequest deleteComicChapterRequest = new DeleteComicChapterRequest();
		deleteComicChapterRequest.setChapterId(1L);
		deleteComicChapterRequest.setComicId(1L);
		deleteComicChapterRequest.setUserId(authorUserId);
		comicChapterService.deleteComicChapter(deleteComicChapterRequest);

		GetRecycleComicChapterRequest request = new GetRecycleComicChapterRequest();
		request.setComicId(1L);
		request.setChapterId(1L);
		request.setUserId(authorUserId);
		ComicChapterDto comicChapter = comicChapterService.getRecycleChapter(request).getData();
		assertThat(comicChapter).isNotNull();
	}

	@Test
	public void getChapterFailedWhenChapterNotExist() {
		GetRecycleComicChapterRequest request = new GetRecycleComicChapterRequest();
		request.setUserId(authorUserId);
		request.setComicId(1L);
		request.setChapterId(-1L);
		assertException(InvalidArgumentException.class, () -> comicChapterService.getRecycleChapter(request));
	}

	@Test
	public void getChapterFailedWhenComicNotExist() {
		GetRecycleComicChapterRequest request = new GetRecycleComicChapterRequest();
		request.setUserId(authorUserId);
		request.setComicId(-1L);
		request.setChapterId(1L);
		assertException(InvalidArgumentException.class, () -> comicChapterService.getRecycleChapter(request));
	}

	@Test
	public void getChapterFailedWhenPermissionDeny() {
		GetRecycleComicChapterRequest request = new GetRecycleComicChapterRequest();
		request.setComicId(-1L);
		request.setChapterId(1L);
		assertException(PermissionDenyException.class, () -> comicChapterService.getRecycleChapter(request));
	}
}
