package com.qiangdong.reader.novel;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.WorksReadHistoryDto;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.enums.common.WorksUpdateStatusEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.novel.GetNovelRequest;
import com.qiangdong.reader.request.works_history.ListWorksReadHistoryRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.novel.GetNovelResponse;
import com.qiangdong.reader.serviceImpl.NovelServiceImpl;
import com.qiangdong.reader.serviceImpl.WorksReadHistoryServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class NovelServiceGetNovelTest extends BaseTest {

	@Autowired
	private NovelServiceImpl novelService;

	@Autowired
	private WorksReadHistoryServiceImpl worksReadHistoryService;

	@Test
	public void getNovelSuccessful() {
		GetNovelResponse response = novelService.getNovel(getNovelRequest, new Novel());
		assertThat(response.getNovel().getNovelId()).isEqualTo(1L);
		assertThat(response.getNovel().getName()).isEqualTo("novel1");
		assertThat(response.getLastUpdateChapter().getId()).isEqualTo(2L);
		assertThat(response.getNovel().getUpdateStatus()).isEqualTo(WorksUpdateStatusEnum.UPDATING);
		assertThat(response.getNovel().getAuthorId()).isEqualTo(1L);
		assertThat(response.getInBookStand()).isTrue();

		Novel novel = novelService.getById(1L);
		assertThat(novel.getClickCount()).isEqualTo(1);

		ListWorksReadHistoryRequest listWorksReadHistoryRequest = new ListWorksReadHistoryRequest();
		listWorksReadHistoryRequest.setUserId(authorUserId);
		listWorksReadHistoryRequest.setWorksType(WorksTypeEnum.NOVEL);
		PageResponse<WorksReadHistoryDto> readHistoryDtoPageResponse =
			worksReadHistoryService.listWorksReadHistory(listWorksReadHistoryRequest);
		assertThat(readHistoryDtoPageResponse.getList().size()).isEqualTo(2);
	}

	@Test
	public void getNovelFailedWhenIdIsInvalid() {
		GetNovelRequest request = new GetNovelRequest();
		request.setNovelId(-1L);
		assertException(InvalidArgumentException.class, () -> novelService.getNovel(request, new Novel()));
	}
}
