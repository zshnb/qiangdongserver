package com.qiangdong.reader.novel;

import cn.hutool.core.collection.CollectionUtil;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.dto.novel.RecommendNovelDto;
import com.qiangdong.reader.enums.common.RecommendTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.novel.AddRecommendNovelRequest;
import com.qiangdong.reader.request.novel.DeleteRecommendNovelRequest;
import com.qiangdong.reader.request.novel.ListRecommendNovelRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelServiceDeleteRecommendNovelTest extends BaseTest {
	@Autowired
	private NovelServiceImpl novelService;

	@Test
	public void deleteRecommendNovelSuccessful() {
		LocalDateTime createAt = LocalDateTime.now();
		LocalDateTime endAt = LocalDateTime.now().plusMinutes(1);
		List<Long> novelIds = CollectionUtil.newArrayList(1L);
		AddRecommendNovelRequest request = new AddRecommendNovelRequest();
		request.setUserId(editorUserId);
		request.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		request.setNovelIds(novelIds);
		request.setCreateAt(createAt);
		request.setEndAt(endAt);
		novelService.addRecommendNovel(request);

		DeleteRecommendNovelRequest deleteRecommendNovelRequest = new DeleteRecommendNovelRequest();
		deleteRecommendNovelRequest.setUserId(editorUserId);
		deleteRecommendNovelRequest.setNovelId(1L);
		deleteRecommendNovelRequest.setCreateAt(createAt);
		deleteRecommendNovelRequest.setEndAt(endAt);
		deleteRecommendNovelRequest.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		novelService.deleteRecommendNovel(deleteRecommendNovelRequest);

		ListRecommendNovelRequest listRecommendNovelRequest = new ListRecommendNovelRequest();
		listRecommendNovelRequest.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		PageResponse<RecommendNovelDto> response = novelService.listRecommendNovel(listRecommendNovelRequest);
		assertThat(response.getList().size()).isEqualTo(0);
	}

	@Test
	public void deleteRecommendNovelFailedWhenNovelNotExist() {
		DeleteRecommendNovelRequest request = new DeleteRecommendNovelRequest();
		request.setUserId(editorUserId);
		request.setNovelId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			novelService.deleteRecommendNovel(request);
		});
	}

	@Test
	public void deleteRecommendNovelFailedWhenPermissionDeny() {
		DeleteRecommendNovelRequest request = new DeleteRecommendNovelRequest();
		request.setNovelId(1L);
		assertException(PermissionDenyException.class, () -> {
			novelService.deleteRecommendNovel(request);
		});
	}
}
