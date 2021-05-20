package com.qiangdong.reader.comic;

import cn.hutool.core.collection.CollectionUtil;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.comic.ComicDto;
import com.qiangdong.reader.dto.comic.RecommendComicDto;
import com.qiangdong.reader.enums.common.RecommendTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.comic.AddRecommendComicRequest;
import com.qiangdong.reader.request.comic.DeleteRecommendComicRequest;
import com.qiangdong.reader.request.comic.ListRecommendComicRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicServiceDeleteRecommendComicTest extends BaseTest {
	@Autowired
	private ComicServiceImpl comicService;

	@Test
	public void deleteRecommendComicSuccessful() {
		LocalDateTime createAt = LocalDateTime.now();
		LocalDateTime endAt = LocalDateTime.now().plusMinutes(1);
		List<Long> comicIds = CollectionUtil.newArrayList(1L);
		AddRecommendComicRequest request = new AddRecommendComicRequest();
		request.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		request.setUserId(editorUserId);
		request.setComicIds(comicIds);
		request.setCreateAt(createAt);
		request.setEndAt(endAt);
		comicService.addRecommendComic(request);

		DeleteRecommendComicRequest deleteRecommendComicRequest = new DeleteRecommendComicRequest();
		deleteRecommendComicRequest.setUserId(editorUserId);
		deleteRecommendComicRequest.setComicId(1L);
		deleteRecommendComicRequest.setCreateAt(createAt);
		deleteRecommendComicRequest.setEndAt(endAt);
		deleteRecommendComicRequest.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		comicService.deleteRecommendComic(deleteRecommendComicRequest);

		ListRecommendComicRequest listRecommendComicRequest = new ListRecommendComicRequest();
		listRecommendComicRequest.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		PageResponse<RecommendComicDto> response = comicService.listRecommendComic(listRecommendComicRequest);
		assertThat(response.getList().size()).isEqualTo(0);
	}

	@Test
	public void deleteRecommendComicFailedWhenNovelNotExist() {
		DeleteRecommendComicRequest request = new DeleteRecommendComicRequest();
		request.setUserId(editorUserId);
		request.setComicId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			comicService.deleteRecommendComic(request);
		});
	}

	@Test
	public void deleteRecommendComicFailedWhenPermissionDeny() {
		DeleteRecommendComicRequest request = new DeleteRecommendComicRequest();
		request.setUserId(userId);
		request.setComicId(1L);
		assertException(PermissionDenyException.class, () -> {
			comicService.deleteRecommendComic(request);
		});
	}
}
