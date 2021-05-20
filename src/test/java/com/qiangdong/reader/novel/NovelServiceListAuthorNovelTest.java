package com.qiangdong.reader.novel;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.request.novel.ListAuthorNovelRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelServiceListAuthorNovelTest extends BaseTest {
	@Autowired
	private NovelServiceImpl novelService;

	@Test
	public void listByAuthorSuccessful() {
		ListAuthorNovelRequest request = new ListAuthorNovelRequest();
		request.setUserId(authorUserId);
		PageResponse<NovelDto> response = novelService.listAuthorNovel(request);
		assertThat(response.getList().size()).isEqualTo(2);
		response.getList().forEach(novelDto -> {
			assertThat(novelDto.getAuthorName()).isEqualTo("author 1");
		});
	}

	@Test
	public void listByUserSuccessful() {
		ListAuthorNovelRequest request = new ListAuthorNovelRequest();
		request.setUserId(userId);
		request.setAuthorId(authorUserId);
		PageResponse<NovelDto> response = novelService.listAuthorNovel(request);
		assertThat(response.getList().size()).isEqualTo(1);
		response.getList().forEach(novelDto -> {
			assertThat(novelDto.getAuthorName()).isEqualTo("author 1");
		});
	}
}
