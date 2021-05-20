package com.qiangdong.reader.novel_chapter;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.novel.NovelChapterDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.novel.DeleteNovelChapterRequest;
import com.qiangdong.reader.request.novel.GetRecycleNovelChapterRequest;
import com.qiangdong.reader.serviceImpl.NovelChapterServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelChapterServiceGetRecycleChapterTest extends BaseTest {
	@Autowired
	private NovelChapterServiceImpl novelChapterService;

	@Test
	public void getChapterSuccessful() {
		DeleteNovelChapterRequest deleteNovelChapterRequest = new DeleteNovelChapterRequest();
		deleteNovelChapterRequest.setChapterId(1L);
		deleteNovelChapterRequest.setNovelId(1L);
		deleteNovelChapterRequest.setUserId(authorUserId);
		novelChapterService.deleteNovelChapter(deleteNovelChapterRequest);

		GetRecycleNovelChapterRequest request = new GetRecycleNovelChapterRequest();
		request.setNovelId(1L);
		request.setChapterId(1L);
		request.setUserId(authorUserId);
		NovelChapterDto novelChapter = novelChapterService.getRecycleChapter(request).getData();
		assertThat(novelChapter).isNotNull();
	}

	@Test
	public void getChapterFailedWhenChapterNotExist() {
		GetRecycleNovelChapterRequest request = new GetRecycleNovelChapterRequest();
		request.setUserId(authorUserId);
		request.setNovelId(1L);
		request.setChapterId(-1L);
		assertException(InvalidArgumentException.class, () -> novelChapterService.getRecycleChapter(request));
	}

	@Test
	public void getChapterFailedWhenNovelNotExist() {
		GetRecycleNovelChapterRequest request = new GetRecycleNovelChapterRequest();
		request.setUserId(authorUserId);
		request.setNovelId(-1L);
		request.setChapterId(1L);
		assertException(InvalidArgumentException.class, () -> novelChapterService.getRecycleChapter(request));
	}

	@Test
	public void getChapterFailedWhenPermissionDeny() {
		GetRecycleNovelChapterRequest request = new GetRecycleNovelChapterRequest();
		request.setUserId(userId);
		request.setNovelId(1L);
		request.setChapterId(1L);
		assertException(PermissionDenyException.class, () -> novelChapterService.getRecycleChapter(request));
	}
}
