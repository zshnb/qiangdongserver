package com.qiangdong.reader.comic_chapter;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.ComicChapter;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.comic.DeleteComicChapterRequest;
import com.qiangdong.reader.serviceImpl.ComicChapterServiceImpl;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicChapterServiceDeleteComicChapterTest extends BaseTest {
	@Autowired
	private ComicChapterServiceImpl comicChapterService;

	@Test
	public void deleteComicChapterSuccessful() {
		DeleteComicChapterRequest request = new DeleteComicChapterRequest();
		request.setComicId(1L);
		request.setChapterId(1L);
		request.setUserId(authorUserId);
		comicChapterService.deleteComicChapter(request);

		List<ComicChapter> list = comicChapterService.list();
		assertThat(list.size()).isEqualTo(3);
	}

	@Test
	public void deleteComicChapterFailedWhenChapterIdIsInvalid() {
		DeleteComicChapterRequest request = new DeleteComicChapterRequest();
		request.setUserId(authorUserId);
		request.setComicId(1L);
		request.setChapterId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			comicChapterService.deleteComicChapter(request);
		});
	}

	@Test
	public void deleteComicChapterFailedWhenPermissionDeny() {
		DeleteComicChapterRequest request = new DeleteComicChapterRequest();
		request.setUserId(userId);
		request.setComicId(1L);
		request.setChapterId(-1L);
		assertException(PermissionDenyException.class, () -> {
			comicChapterService.deleteComicChapter(request);
		});
	}
}
