package com.qiangdong.reader.draft;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Draft;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.draft.DeleteDraftRequest;
import com.qiangdong.reader.serviceImpl.DraftServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DraftServiceDeleteDraftTest extends BaseTest {
	@Autowired
	private DraftServiceImpl draftService;

	@Test
	public void deleteSuccessful() {
		DeleteDraftRequest request = new DeleteDraftRequest();
		request.setDraftId(1L);
		draftService.deleteDraft(request);
		Draft draft = draftService.getById(1L);
		assertThat(draft).isNull();
	}

	@Test
	public void deleteFailedWhenDraftNotExist() {
		DeleteDraftRequest request = new DeleteDraftRequest();
		request.setDraftId(-1L);
		assertException(InvalidArgumentException.class, () -> draftService.deleteDraft(request));
	}
}
