package com.qiangdong.reader.comic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.comic.ComicDto;
import com.qiangdong.reader.request.comic.ListAuthorComicRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicServiceListAuthorComicTest extends BaseTest {
	@Autowired
	private ComicServiceImpl comicService;

	@Test
	public void listByAuthorSuccessful() {
		ListAuthorComicRequest request = new ListAuthorComicRequest();
		request.setUserId(1L);
		PageResponse<ComicDto> response = comicService.listAuthorComic(request);
		assertThat(response.getList().size()).isEqualTo(2);
		response.getList().forEach(comicDto -> assertThat(comicDto.getAuthorName()).isEqualTo("author 1"));
	}

	@Test
	public void listByUserSuccessful() {
		ListAuthorComicRequest request = new ListAuthorComicRequest();
		request.setUserId(1L);
		request.setAuthorId(authorUserId);
		PageResponse<ComicDto> response = comicService.listAuthorComic(request);
		assertThat(response.getList().size()).isEqualTo(1);
		response.getList().forEach(comicDto -> assertThat(comicDto.getAuthorName()).isEqualTo("author 1"));
	}
}
