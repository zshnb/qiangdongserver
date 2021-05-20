package com.qiangdong.reader.draft;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dao.ComicChapterMapper;
import com.qiangdong.reader.entity.ComicChapter;
import com.qiangdong.reader.entity.Draft;
import com.qiangdong.reader.enums.common.WorksChapterTypeEnum;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.draft.AddOrUpdateDraftRequest;
import com.qiangdong.reader.request.draft.PublishDraftRequest;
import com.qiangdong.reader.serviceImpl.DraftServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DraftServicePublishDraftTest extends BaseTest {
	@Autowired
	private DraftServiceImpl draftService;

	@Autowired
	private ComicChapterMapper comicChapterMapper;

	@Test
	public void publishDraftSuccessful() {
		PublishDraftRequest request = new PublishDraftRequest();
		request.setDraftId(1L);
		draftService.publishDraft(request);
		Draft draft = draftService.getById(1L);
		assertThat(draft).isNull();
		ComicChapter comicChapter = comicChapterMapper.selectOne(new QueryWrapper<ComicChapter>()
			.eq("title", "draft title"));
		assertThat(comicChapter).isNotNull();
		assertThat(comicChapter.getIndex()).isEqualTo(3);
	}

	@Test
	public void publishDraftFailedWhenDraftNotExist() {
		PublishDraftRequest request = new PublishDraftRequest();
		request.setDraftId(-1L);
		assertException(InvalidArgumentException.class, () -> draftService.publishDraft(request));
	}

	@Test
	public void publishDraftFailedWhenTitleDuplicated() {
		AddOrUpdateDraftRequest addOrUpdateDraftRequest = new AddOrUpdateDraftRequest();
		addOrUpdateDraftRequest.setWorksId(1L);
		addOrUpdateDraftRequest.setType(WorksTypeEnum.COMIC);
		addOrUpdateDraftRequest.setTitle("comic chapter 1-1");
		Draft draft = draftService.addOrUpdateDraft(addOrUpdateDraftRequest).getData();

		PublishDraftRequest request = new PublishDraftRequest();
		request.setDraftId(draft.getId());
		request.setWorksChapterType(WorksChapterTypeEnum.PUBLIC);
		assertException(InvalidArgumentException.class, () -> draftService.publishDraft(request));
	}
}
