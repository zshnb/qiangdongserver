package com.qiangdong.reader.comic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.comic.ComicDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.comic.DeleteComicRequest;
import com.qiangdong.reader.request.comic.RestoreDeletedComicRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicServiceRestoreDeletedComicTest extends BaseTest {
	@Autowired
	private ComicServiceImpl comicService;

	@Test
	public void restoreSuccessful() {
		DeleteComicRequest deleteComicRequest = new DeleteComicRequest();
		deleteComicRequest.setComicId(1L);
		deleteComicRequest.setUserId(authorUserId);
		comicService.deleteComic(deleteComicRequest);

		BaseRequest baseRequest = new BaseRequest();
		baseRequest.setUserId(authorUserId);
		PageResponse<ComicDto> response = comicService.listDeletedComic(baseRequest);
		assertThat(response.getList().size()).isEqualTo(1);
		assertThat(response.getList().get(0).getComicId()).isEqualTo(1L);

		RestoreDeletedComicRequest request = new RestoreDeletedComicRequest();
		request.setComicId(1L);
		request.setUserId(authorUserId);
		comicService.restoreDeletedComic(request);

		List<Comic> comics = comicService.list();
		assertThat(comics.size()).isEqualTo(2);
	}

	@Test
	public void restoreFailedWhenNotExist() {
		RestoreDeletedComicRequest request = new RestoreDeletedComicRequest();
		request.setComicId(1L);
		request.setUserId(authorUserId);
		assertException(InvalidArgumentException.class, () -> comicService.restoreDeletedComic(request));
	}

	@Test
	public void restoreFailedWhenPermissonDeny() {
		RestoreDeletedComicRequest request = new RestoreDeletedComicRequest();
		request.setComicId(1L);
		request.setUserId(userId);
		assertException(PermissionDenyException.class, () -> comicService.restoreDeletedComic(request));
	}
}
