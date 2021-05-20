package com.qiangdong.reader.works_read_history;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.WorksReadHistoryDto;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.works_history.ListWorksReadHistoryRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.WorksReadHistoryServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorksReadHistoryServiceListWorksReadHistoryTest extends BaseTest {
	@Autowired
	private WorksReadHistoryServiceImpl worksReadHistoryService;

	@Test
	public void listHistorySuccessful() {
		ListWorksReadHistoryRequest request = new ListWorksReadHistoryRequest();
		request.setUserId(1L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		PageResponse<WorksReadHistoryDto> response = worksReadHistoryService.listWorksReadHistory(request);
		assertThat(response.getList().size()).isEqualTo(2);

		WorksReadHistoryDto worksReadHistory = response.getList().get(0);
		assertThat(worksReadHistory.getWorksName()).isEqualTo("novel1");
		assertThat(worksReadHistory.getAuthorName()).isEqualTo("author 1");
		assertThat(worksReadHistory.getTypeName()).isEqualTo("分类1|分类1-novel");
	}
}
