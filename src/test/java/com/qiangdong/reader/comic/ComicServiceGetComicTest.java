package com.qiangdong.reader.comic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.WorksReadHistoryDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.enums.common.WorksUpdateStatusEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comic.GetComicRequest;
import com.qiangdong.reader.request.works_history.ListWorksReadHistoryRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.comic.GetComicResponse;
import com.qiangdong.reader.serviceImpl.ComicServiceImpl;
import com.qiangdong.reader.serviceImpl.WorksReadHistoryServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ComicServiceGetComicTest extends BaseTest {

	@Autowired
	private ComicServiceImpl comicService;

	@Autowired
	private WorksReadHistoryServiceImpl worksReadHistoryService;

	@Test
	public void getComicSuccessful() {
		GetComicResponse response = comicService.getComic(getComicRequest, new Comic());
		assertThat(response.getComic().getComicId()).isEqualTo(1L);
		assertThat(response.getLastUpdateChapter().getId()).isEqualTo(2L);
		assertThat(response.getComic().getName()).isEqualTo("comic1");
		assertThat(response.getComic().getUpdateStatus()).isEqualTo(WorksUpdateStatusEnum.UPDATING);
		assertThat(response.getComic().getAuthorId()).isEqualTo(1L);
		assertThat(response.getInBookStand()).isTrue();

		ListWorksReadHistoryRequest listWorksReadHistoryRequest = new ListWorksReadHistoryRequest();
		listWorksReadHistoryRequest.setUserId(authorUserId);
		listWorksReadHistoryRequest.setWorksType(WorksTypeEnum.COMIC);
		PageResponse<WorksReadHistoryDto> readHistoryDtoPageResponse =
			worksReadHistoryService.listWorksReadHistory(listWorksReadHistoryRequest);
		assertThat(readHistoryDtoPageResponse.getList().size()).isEqualTo(1);
	}

	@Test
	public void getComicFailedWhenIdIsInvalid() {
		GetComicRequest request = new GetComicRequest();
		request.setComicId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			comicService.getComic(request, new Comic());
		});
	}
}
