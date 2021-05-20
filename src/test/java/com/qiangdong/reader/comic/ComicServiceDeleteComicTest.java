package com.qiangdong.reader.comic;

import cn.hutool.core.collection.CollectionUtil;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.comic.ComicDto;
import com.qiangdong.reader.dto.comic.RecommendComicDto;
import com.qiangdong.reader.enums.common.RecommendTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.comic.AddRecommendComicRequest;
import com.qiangdong.reader.request.comic.DeleteComicRequest;
import com.qiangdong.reader.request.comic.ListAuthorComicRequest;
import com.qiangdong.reader.request.comic.ListRecommendComicRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicServiceDeleteComicTest extends BaseTest {
	@Autowired
	private ComicServiceImpl comicService;

	@Test
	public void deleteComicSuccessful() {
		List<Long> comicIds = CollectionUtil.newArrayList(1L);
		AddRecommendComicRequest request = new AddRecommendComicRequest();
		request.setUserId(editorUserId);
		request.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		request.setComicIds(comicIds);
		request.setEndAt(LocalDateTime.now().plusMinutes(1));
		comicService.addRecommendComic(request);

		DeleteComicRequest deleteComicRequest = new DeleteComicRequest();
		deleteComicRequest.setComicId(1L);
		deleteComicRequest.setUserId(authorUserId);
		comicService.deleteComic(deleteComicRequest);

		ListAuthorComicRequest listAuthorComicRequest = new ListAuthorComicRequest();
		listAuthorComicRequest.setAuthorId(1L);
		PageResponse<ComicDto> response = comicService.listAuthorComic(listAuthorComicRequest);
		assertThat(response.getList().size()).isEqualTo(0);

		ListRecommendComicRequest listRecommendComicRequest = new ListRecommendComicRequest();
		listRecommendComicRequest.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		PageResponse<RecommendComicDto> recommendComicDtoPageResponse =
			comicService.listRecommendComic(listRecommendComicRequest);
		assertThat(recommendComicDtoPageResponse.getList().size()).isEqualTo(0);
	}

	@Test
	public void deleteComicFailedWhenNovelIdIsInvalid() {
		DeleteComicRequest request = new DeleteComicRequest();
		request.setUserId(authorUserId);
		request.setComicId(-1L);
		assertException(InvalidArgumentException.class, () -> comicService.deleteComic(request));
	}

	@Test
	public void deleteComicFailedWhenPermissionDeny() {
		DeleteComicRequest request = new DeleteComicRequest();
		request.setUserId(userId);
		request.setComicId(1L);
		assertException(PermissionDenyException.class, () -> comicService.deleteComic(request));
	}
}
