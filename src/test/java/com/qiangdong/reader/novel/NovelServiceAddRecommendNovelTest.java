package com.qiangdong.reader.novel;

import cn.hutool.core.collection.CollectionUtil;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.dto.novel.RecommendNovelDto;
import com.qiangdong.reader.enums.common.RecommendTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.novel.AddRecommendNovelRequest;
import com.qiangdong.reader.request.novel.ListRecommendNovelRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelServiceAddRecommendNovelTest extends BaseTest {
	@Autowired
	private NovelServiceImpl novelService;

	@Test
	public void addRecommendNovelSuccessful() {
		List<Long> novelIds = CollectionUtil.newArrayList(1L);
		AddRecommendNovelRequest request = new AddRecommendNovelRequest();
		request.setUserId(editorUserId);
		request.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		request.setNovelIds(novelIds);
		request.setEndAt(LocalDateTime.now().plusMinutes(1));
		novelService.addRecommendNovel(request);

		ListRecommendNovelRequest listRecommendNovelRequest = new ListRecommendNovelRequest();
		listRecommendNovelRequest.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		PageResponse<RecommendNovelDto> response = novelService.listRecommendNovel(listRecommendNovelRequest);
		RecommendNovelDto recommendNovelDto = response.getList().stream()
			.filter(it -> it.getNovelId().equals(1L))
			.findFirst()
			.get();
		assertThat(response.getList().size()).isEqualTo(1);
		assertThat(recommendNovelDto.getName()).isEqualTo("novel1");
		assertThat(recommendNovelDto.getAuthorName()).isEqualTo("author 1");
		assertThat(recommendNovelDto.getTypeName()).isEqualTo("分类1|分类1-novel");
	}

	@Test
	public void addRecommendNovelFailedWhenNovelNotExist() {
		List<Long> novelIds = CollectionUtil.newArrayList(1L, 2L, 3L);
		AddRecommendNovelRequest request = new AddRecommendNovelRequest();
		request.setUserId(editorUserId);
		request.setRecommendType(RecommendTypeEnum.EDITOR_RECOMMEND);
		request.setNovelIds(novelIds);
		request.setEndAt(LocalDateTime.now().plusMinutes(1));
		assertException(InvalidArgumentException.class, () -> {
			novelService.addRecommendNovel(request);
		});
	}

	@Test
	public void addFreeNovelFailedWhenEndAtNotSame() {
		LocalDateTime endAt = LocalDateTime.now().plusMinutes(1);
		List<Long> novelIds = CollectionUtil.newArrayList(1L);
		AddRecommendNovelRequest request = new AddRecommendNovelRequest();
		request.setUserId(editorUserId);
		request.setRecommendType(RecommendTypeEnum.FREE);
		request.setNovelIds(novelIds);
		request.setEndAt(endAt);
		novelService.addRecommendNovel(request);

		novelIds = CollectionUtil.newArrayList(2L);
		request.setRecommendType(RecommendTypeEnum.FREE);
		request.setNovelIds(novelIds);
		request.setEndAt(LocalDateTime.now());
		assertException(InvalidArgumentException.class, () -> novelService.addRecommendNovel(request));
	}
}
