package com.qiangdong.reader.novel;

import cn.hutool.core.collection.CollectionUtil;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.dto.novel.RecommendNovelDto;
import com.qiangdong.reader.enums.common.RecommendTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.novel.AddRecommendNovelRequest;
import com.qiangdong.reader.request.novel.DeleteNovelRequest;
import com.qiangdong.reader.request.novel.ListAuthorNovelRequest;
import com.qiangdong.reader.request.novel.ListRecommendNovelRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelServiceDeleteNovelTest extends BaseTest {
	@Autowired
	private NovelServiceImpl novelService;

	@Test
	public void deleteNovelSuccessful() {
		List<Long> novelIds = CollectionUtil.newArrayList(1L);
		AddRecommendNovelRequest request = new AddRecommendNovelRequest();
		request.setUserId(editorUserId);
		request.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		request.setNovelIds(novelIds);
		request.setEndAt(LocalDateTime.now().plusMinutes(1));
		novelService.addRecommendNovel(request);

		DeleteNovelRequest deleteNovelRequest = new DeleteNovelRequest();
		deleteNovelRequest.setNovelId(1L);
		deleteNovelRequest.setUserId(authorUserId);
		novelService.deleteNovel(deleteNovelRequest);

		ListAuthorNovelRequest listAuthorNovelRequest = new ListAuthorNovelRequest();
		listAuthorNovelRequest.setAuthorId(1L);
		PageResponse<NovelDto> response = novelService.listAuthorNovel(listAuthorNovelRequest);
		assertThat(response.getList().size()).isEqualTo(0);

		ListRecommendNovelRequest listRecommendNovelRequest = new ListRecommendNovelRequest();
		listRecommendNovelRequest.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		PageResponse<RecommendNovelDto> recommendNovelDtoPageResponse = novelService.listRecommendNovel(listRecommendNovelRequest);
		assertThat(recommendNovelDtoPageResponse.getList().size()).isEqualTo(0);
	}

	@Test
	public void deleteNovelFailedWhenNovelIdIsInvalid() {
		DeleteNovelRequest request = new DeleteNovelRequest();
		request.setUserId(authorUserId);
		request.setNovelId(-1L);
		assertException(InvalidArgumentException.class, () -> novelService.deleteNovel(request));
	}

	@Test
	public void deleteNovelFailedWhenPermissionDeny() {
		DeleteNovelRequest request = new DeleteNovelRequest();
		request.setUserId(userId);
		request.setNovelId(1L);
		assertException(PermissionDenyException.class, () -> novelService.deleteNovel(request));
	}
}
