package com.qiangdong.reader.comic_chapter;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.ComicChapter;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comic.ListComicChaptersRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.ComicChapterServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicChapterServiceListComicChaptersTest extends BaseTest {
	@Autowired
	private ComicChapterServiceImpl comicChapterService;

	@Test
	public void listSuccessful() {
		ListComicChaptersRequest request = new ListComicChaptersRequest();
		request.setComicId(1L);
		PageResponse<ComicChapter> response = comicChapterService.listComicChapters(request);
		assertThat(response.getList().size()).isEqualTo(2);
	}

	@Test
	public void listChaptersFailedWhenNovelNotExist() {
		ListComicChaptersRequest request = new ListComicChaptersRequest();
		request.setComicId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			comicChapterService.listComicChapters(request);
		});
	}
}
