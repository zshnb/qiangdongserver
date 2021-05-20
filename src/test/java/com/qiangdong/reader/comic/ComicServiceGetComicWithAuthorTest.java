package com.qiangdong.reader.comic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.response.comic.GetComicResponse;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicServiceGetComicWithAuthorTest extends BaseTest {
	@Autowired
	private ComicServiceImpl comicService;

	@Test
	public void getComicWithAuthorSuccessful() {
		GetComicResponse response = comicService.getComicWithAuthor(getComicRequest);
		assertThat(response.getComic().getComicId()).isEqualTo(1L);
	}

	@Test
	public void getComicWithAuthorFailedWhenNotExist() {
		getComicRequest.setComicId(-1L);
		assertException(InvalidArgumentException.class, () -> comicService.getComicWithAuthor(getComicRequest));
	}

	@Test
	public void getComicWithAuthorFailedWhenNoPermission() {
		getComicRequest.setUserId(userId);
		assertException(PermissionDenyException.class, () -> comicService.getComicWithAuthor(getComicRequest));
	}
}
