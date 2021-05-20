package com.qiangdong.reader.comic_chapter;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.comic.ComicChapterDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.comic.DeleteComicChapterRequest;
import com.qiangdong.reader.request.comic.ListRecycleComicChaptersRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.ComicChapterServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicChapterServiceListRecycleChaptersTest extends BaseTest {
	@Autowired
	private ComicChapterServiceImpl comicChapterService;

	@Test
	public void listSuccessful() {
		DeleteComicChapterRequest deleteComicChapterRequest = new DeleteComicChapterRequest();
		deleteComicChapterRequest.setComicId(1L);
		deleteComicChapterRequest.setChapterId(1L);
		deleteComicChapterRequest.setUserId(authorUserId);
		comicChapterService.deleteComicChapter(deleteComicChapterRequest);

		ListRecycleComicChaptersRequest request = new ListRecycleComicChaptersRequest();
		request.setComicId(1L);
		request.setUserId(authorUserId);
		PageResponse<ComicChapterDto> response = comicChapterService.listRecycleChapters(request);
		assertThat(response.getList().size()).isEqualTo(1);
	}

	@Test
	public void listFailedWhenNovelNotExist() {
		ListRecycleComicChaptersRequest request = new ListRecycleComicChaptersRequest();
		request.setUserId(authorUserId);
		request.setComicId(-1L);
		assertException(InvalidArgumentException.class, () -> comicChapterService.listRecycleChapters(request));
	}

	@Test
	public void listFailedWhenPermissionDeny() {
		ListRecycleComicChaptersRequest request = new ListRecycleComicChaptersRequest();
		request.setComicId(-1L);
		assertException(PermissionDenyException.class, () -> comicChapterService.listRecycleChapters(request));
	}
}
