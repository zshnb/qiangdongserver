package com.qiangdong.reader.draft;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Draft;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.draft.AddOrUpdateDraftRequest;
import com.qiangdong.reader.serviceImpl.DraftServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DraftServiceAddOrUpdateDraftTest extends BaseTest {
	@Autowired
	private DraftServiceImpl draftService;

	@Test
	public void addDraftSuccessful() {
		AddOrUpdateDraftRequest request = new AddOrUpdateDraftRequest();
		request.setWorksId(1L);
		request.setType(WorksTypeEnum.NOVEL);
		draftService.addOrUpdateDraft(request);
		Draft draft = draftService.getOne(new QueryWrapper<Draft>()
			.eq("works_id", 1L)
			.eq("type", 2));
		assertThat(draft.getId()).isNotZero();
	}

	@Test
	public void updateDraftSuccessful() {
		AddOrUpdateDraftRequest request = new AddOrUpdateDraftRequest();
		request.setDraftId(1L);
		request.setWorksId(1L);
		request.setType(WorksTypeEnum.COMIC);
		request.setPictureUrl("picture");
		draftService.addOrUpdateDraft(request);
		Draft draft = draftService.getById(1L);
		assertThat(draft.getPictureUrl()).isEqualTo("picture");
	}

	@Test
	public void addDraftFailedWhenNovelNotExist() {
		AddOrUpdateDraftRequest request = new AddOrUpdateDraftRequest();
		request.setDraftId(1L);
		request.setType(WorksTypeEnum.NOVEL);
		assertException(InvalidArgumentException.class, () -> {
			draftService.addOrUpdateDraft(request);
		});
	}

	@Test
	public void updateDraftFailedWhenWorksTypeInvalid() {
		AddOrUpdateDraftRequest request = new AddOrUpdateDraftRequest();
		request.setDraftId(1L);
		request.setType(WorksTypeEnum.NOVEL);
		assertException(InvalidArgumentException.class, () -> {
			draftService.addOrUpdateDraft(request);
		});

	}
}
