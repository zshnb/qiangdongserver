package com.qiangdong.reader.draft;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Draft;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.draft.ListWorksDraftRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.DraftServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DraftServiceListWorksDraftTest extends BaseTest {
	@Autowired
	private DraftServiceImpl draftService;

	@Test
	public void listDraftSuccessful() {
		ListWorksDraftRequest request = new ListWorksDraftRequest();
		request.setUserId(1L);
		request.setWorksId(1L);
		request.setWorksType(WorksTypeEnum.COMIC);
		PageResponse<Draft> response = draftService.listWorksDraft(request);
		assertThat(response.getList().size()).isEqualTo(1);
	}

	@Test
	public void listDraftFailedWhenNovelNotExist() {
		ListWorksDraftRequest request = new ListWorksDraftRequest();
		request.setUserId(1L);
		request.setWorksId(-1L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		assertException(InvalidArgumentException.class, () -> draftService.listWorksDraft(request));
	}

	@Test
	public void listDraftFailedWhenComicNotExist() {
		ListWorksDraftRequest request = new ListWorksDraftRequest();
		request.setUserId(1L);
		request.setWorksId(-1L);
		request.setWorksType(WorksTypeEnum.COMIC);
		assertException(InvalidArgumentException.class, () -> draftService.listWorksDraft(request));
	}
}
