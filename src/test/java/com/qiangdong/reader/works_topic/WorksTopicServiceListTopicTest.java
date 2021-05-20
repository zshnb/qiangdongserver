package com.qiangdong.reader.works_topic;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.WorksTopicDto;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.works_topic.ListWorksTopicRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.WorksTopicServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorksTopicServiceListTopicTest extends BaseTest {

	@Autowired
	private WorksTopicServiceImpl topicService;

	@Test
	public void listTopicSuccessful() {
		ListWorksTopicRequest request = new ListWorksTopicRequest();
		request.setUserId(editorUserId);
		request.setTypeId(2L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		PageResponse<WorksTopicDto> response = topicService.listTopic(request);
		assertThat(response.getList().size()).isEqualTo(2);
	}

	@Test
	public void listTopicWhenTypeNoExist() {
		ListWorksTopicRequest request = new ListWorksTopicRequest();
		request.setUserId(editorUserId);
		request.setTypeId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			topicService.listTopic(request);
		});
	}

	@Test
	public void listTopicFailedWhenPermissionDeny() {
		ListWorksTopicRequest request = new ListWorksTopicRequest();
		request.setUserId(userId);
		request.setTypeId(2L);
		request.setWorksType(WorksTypeEnum.NOVEL);
		assertException(PermissionDenyException.class, () -> {
			topicService.listTopic(request);
		});
	}
}
