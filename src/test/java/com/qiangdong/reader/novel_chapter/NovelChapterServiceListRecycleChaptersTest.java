package com.qiangdong.reader.novel_chapter;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.novel.NovelChapterDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.novel.DeleteNovelChapterRequest;
import com.qiangdong.reader.request.novel.ListRecycleNovelChaptersRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.NovelChapterServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelChapterServiceListRecycleChaptersTest extends BaseTest {
	@Autowired
	private NovelChapterServiceImpl novelChapterService;

	@Test
	public void listSuccessful() {
		DeleteNovelChapterRequest deleteNovelChapterRequest = new DeleteNovelChapterRequest();
		deleteNovelChapterRequest.setNovelId(1L);
		deleteNovelChapterRequest.setChapterId(1L);
		deleteNovelChapterRequest.setUserId(authorUserId);
		novelChapterService.deleteNovelChapter(deleteNovelChapterRequest);

		ListRecycleNovelChaptersRequest request = new ListRecycleNovelChaptersRequest();
		request.setNovelId(1L);
		request.setUserId(authorUserId);
		PageResponse<NovelChapterDto> response = novelChapterService.listRecycleChapters(request);
		assertThat(response.getList().size()).isEqualTo(1);
	}

	@Test
	public void listFailedWhenNovelNotExist() {
		ListRecycleNovelChaptersRequest request = new ListRecycleNovelChaptersRequest();
		request.setNovelId(-1L);
		request.setUserId(authorUserId);
		assertException(InvalidArgumentException.class, () -> novelChapterService.listRecycleChapters(request));
	}

	@Test
	public void listFailedWhenPermissionDeny() {
		ListRecycleNovelChaptersRequest request = new ListRecycleNovelChaptersRequest();
		request.setNovelId(1L);
		request.setUserId(userId);
		assertException(PermissionDenyException.class, () -> novelChapterService.listRecycleChapters(request));
	}
}
