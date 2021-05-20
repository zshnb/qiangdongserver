package com.qiangdong.reader.novel_chapter;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.NovelChapter;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.novel.ListNovelChaptersRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.NovelChapterServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelChapterServiceListNovelChaptersTest extends BaseTest {
	@Autowired
	private NovelChapterServiceImpl novelChapterService;

	@Test
	public void listSuccessful() {
		ListNovelChaptersRequest request = new ListNovelChaptersRequest();
		request.setNovelId(1L);
		PageResponse<NovelChapter> response = novelChapterService.listNovelChapters(request);
		assertThat(response.getList().size()).isEqualTo(2);
	}

	@Test
	public void listChaptersFailedWhenNovelNotExist() {
		ListNovelChaptersRequest request = new ListNovelChaptersRequest();
		request.setNovelId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			novelChapterService.listNovelChapters(request);
		});
	}
}
