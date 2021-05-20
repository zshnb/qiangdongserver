package com.qiangdong.reader.works_read_history;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.WorksReadHistory;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.works_history.AddOrUpdateWorksHistoryRequest;
import com.qiangdong.reader.serviceImpl.WorksReadHistoryServiceImpl;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorksReadHistoryServiceAddOrUpdateWorksReadHistoryTest extends BaseTest {
	@Autowired
	private WorksReadHistoryServiceImpl worksReadHistoryService;

	@Test
	public void addHistorySuccessful() {
		AddOrUpdateWorksHistoryRequest request = new AddOrUpdateWorksHistoryRequest();
		request.setWorksId(1L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		WorksReadHistory worksReadHistory = worksReadHistoryService.addOrUpdateWorksReadHistory(request).getData();
		assertThat(worksReadHistory.getId()).isNotZero();
	}

	@Test
	public void updateHistorySuccessful() {
		AddOrUpdateWorksHistoryRequest request = new AddOrUpdateWorksHistoryRequest();
		request.setHistoryId(1L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		request.setWorksId(1L);
		worksReadHistoryService.addOrUpdateWorksReadHistory(request);
		WorksReadHistory worksReadHistory = worksReadHistoryService.getById(1L);
		assertThat(Duration.between(worksReadHistory.getUpdateAt(), LocalDateTime.now()).getSeconds())
			.isLessThanOrEqualTo(1L);
	}

	@Test
	public void addHistoryFailedWhenNovelNotExist() {
		AddOrUpdateWorksHistoryRequest request = new AddOrUpdateWorksHistoryRequest();
		request.setWorksId(-1L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		assertException(InvalidArgumentException.class, () -> {
			worksReadHistoryService.addOrUpdateWorksReadHistory(request);
		});
	}

	@Test
	public void updateHistoryFailedWhenHistoryNotExist() {
		AddOrUpdateWorksHistoryRequest request = new AddOrUpdateWorksHistoryRequest();
		request.setHistoryId(-1L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		request.setWorksId(1L);
		assertException(InvalidArgumentException.class, () -> {
			worksReadHistoryService.addOrUpdateWorksReadHistory(request);
		});
	}
}
