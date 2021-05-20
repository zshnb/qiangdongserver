package com.qiangdong.reader.comic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.comic.ComicDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.comic.DeleteComicRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicServiceListDeletedComicTest extends BaseTest {
	@Autowired
	private ComicServiceImpl comicService;

	@Test
	public void listSuccessful() {
		List<Comic> comics = comicService.list();
		assertThat(comics.size()).isEqualTo(2);

		DeleteComicRequest deleteComicRequest = new DeleteComicRequest();
		deleteComicRequest.setUserId(authorUserId);
		deleteComicRequest.setComicId(1L);
		comicService.deleteComic(deleteComicRequest);

		BaseRequest request = new BaseRequest();
		request.setUserId(authorUserId);
		PageResponse<ComicDto> response = comicService.listDeletedComic(request);
		assertThat(response.getList().size()).isEqualTo(1);
		assertThat(response.getList().get(0).getComicId()).isEqualTo(1L);
	}

	@Test
	public void listFailedWhenPermissionDeny() {
		assertException(PermissionDenyException.class, () -> comicService.listDeletedComic(new BaseRequest()));
	}
}
