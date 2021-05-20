package com.qiangdong.reader.novel_chapter;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.novel.DeleteNovelChapterRequest;
import com.qiangdong.reader.request.novel.GetNovelChapterRequest;
import com.qiangdong.reader.serviceImpl.NovelChapterServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelChapterServiceDeleteNovelChapterTest extends BaseTest {
	@Autowired
	private NovelChapterServiceImpl novelChapterService;

	@Test
	public void deleteNovelChapterSuccessful() {
		DeleteNovelChapterRequest request = new DeleteNovelChapterRequest();
		request.setNovelId(1L);
		request.setChapterId(1L);
		request.setUserId(authorUserId);
		novelChapterService.deleteNovelChapter(request);

		assertException(InvalidArgumentException.class, () -> {
			GetNovelChapterRequest getNovelChapterRequest = new GetNovelChapterRequest();
			getNovelChapterRequest.setNovelId(1L);
			getNovelChapterRequest.setChapterId(1L);
			novelChapterService.getNovelChapter(getNovelChapterRequest);
		});
	}

	@Test
	public void deleteNovelChapterFailedWhenChapterIdIsInvalid() {
		DeleteNovelChapterRequest request = new DeleteNovelChapterRequest();
		request.setNovelId(1L);
		request.setChapterId(-1L);
		request.setUserId(authorUserId);
		assertException(InvalidArgumentException.class, () -> novelChapterService.deleteNovelChapter(request));
	}

	@Test
	public void deleteNovelChapterFailedWhenPermissionDeny() {
		DeleteNovelChapterRequest request = new DeleteNovelChapterRequest();
		request.setNovelId(1L);
		request.setChapterId(-1L);
		request.setUserId(userId);
		assertException(PermissionDenyException.class, () -> novelChapterService.deleteNovelChapter(request));
	}
}
