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
import com.qiangdong.reader.request.comic.ListRecommendComicRequest;
import com.qiangdong.reader.request.novel.AddRecommendNovelRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicServiceAddRecommendComicTest extends BaseTest {
	@Autowired
	private ComicServiceImpl comicService;

	@Test
	public void addRecommendComicSuccessful() {
		List<Long> comicIds = CollectionUtil.newArrayList(1L);
		AddRecommendComicRequest request = new AddRecommendComicRequest();
		request.setUserId(editorUserId);
		request.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		request.setComicIds(comicIds);
		request.setEndAt(LocalDateTime.now().plusMinutes(1));
		comicService.addRecommendComic(request);

		ListRecommendComicRequest listRecommendComicRequest = new ListRecommendComicRequest();
		listRecommendComicRequest.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		PageResponse<RecommendComicDto> response = comicService.listRecommendComic(listRecommendComicRequest);
		assertThat(response.getList().size()).isEqualTo(1);
	}

	@Test
	public void addRecommendComicFailedWhenComicNotExist() {
		List<Long> comicIds = CollectionUtil.newArrayList(1L, 2L, 3L);
		AddRecommendComicRequest request = new AddRecommendComicRequest();
		request.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		request.setUserId(editorUserId);
		request.setComicIds(comicIds);
		request.setEndAt(LocalDateTime.now().plusMinutes(1));
		assertException(InvalidArgumentException.class, () -> {
			comicService.addRecommendComic(request);
		});
	}

	@Test
	public void addRecommendComicFailedWhenPermissionDeny() {
		List<Long> comicIds = CollectionUtil.newArrayList(1L, 2L, 3L);
		AddRecommendComicRequest request = new AddRecommendComicRequest();
		request.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		request.setUserId(userId);
		request.setComicIds(comicIds);
		request.setEndAt(LocalDateTime.now().plusMinutes(1));
		assertException(PermissionDenyException.class, () -> {
			comicService.addRecommendComic(request);
		});
	}

	@Test
	public void addFreeComicFailedWhenEndAtNotSame() {
		LocalDateTime endAt = LocalDateTime.now().plusMinutes(1);
		List<Long> comicIds = CollectionUtil.newArrayList(1L);
		AddRecommendComicRequest request = new AddRecommendComicRequest();
		request.setUserId(editorUserId);
		request.setRecommendType(RecommendTypeEnum.FREE);
		request.setComicIds(comicIds);
		request.setEndAt(endAt);
		comicService.addRecommendComic(request);

		comicIds = CollectionUtil.newArrayList(2L);
		request.setRecommendType(RecommendTypeEnum.FREE);
		request.setComicIds(comicIds);
		request.setEndAt(LocalDateTime.now());
		assertException(InvalidArgumentException.class, () -> comicService.addRecommendComic(request));
	}
}
