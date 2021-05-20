package com.qiangdong.reader.works_topic;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.WorksTopicDto;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.works_topic.DeleteWorksTopicRequest;
import com.qiangdong.reader.request.works_topic.ListWorksTopicRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.WorksTopicServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorksTopicServiceDeleteTopicTest extends BaseTest {

	@Autowired
	private WorksTopicServiceImpl topicService;

	@Test
	public void deleteTopicSuccessful() {
		DeleteWorksTopicRequest request = new DeleteWorksTopicRequest();
		request.setUserId(editorUserId);
		request.setTopicId(1L);
		topicService.deleteTopic(request);

		ListWorksTopicRequest request1 = new ListWorksTopicRequest();
		request1.setUserId(editorUserId);
		request1.setTypeId(2L);
		request1.setWorksType(WorksTypeEnum.NOVEL);
		PageResponse<WorksTopicDto> response = topicService.listTopic(request1);
		assertThat(response.getList().size()).isEqualTo(1);
	}

	@Test
	public void deleteTopicWhenTopicNoExist() {
		DeleteWorksTopicRequest request = new DeleteWorksTopicRequest();
		request.setUserId(editorUserId);
		request.setTopicId(-1L);
		assertException(InvalidArgumentException.class, ()->{
			topicService.deleteTopic(request);
		});
	}

	@Test
	public void deleteTopicFailedWhenPermissionDeny() {
		DeleteWorksTopicRequest request = new DeleteWorksTopicRequest();
		request.setUserId(userId);
		request.setTopicId(1L);
		assertException(PermissionDenyException.class, ()->{
			topicService.deleteTopic(request);
		});
	}
}
