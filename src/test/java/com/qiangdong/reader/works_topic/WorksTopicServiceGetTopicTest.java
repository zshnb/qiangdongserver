package com.qiangdong.reader.works_topic;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.works_topic.GetTopicRequest;
import com.qiangdong.reader.response.works_topic.GetWorksTopicResponse;
import com.qiangdong.reader.serviceImpl.WorksTopicServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorksTopicServiceGetTopicTest extends BaseTest {

	@Autowired
	private WorksTopicServiceImpl topicService;

	@Test
	public void getTopicSuccessful() {
		GetTopicRequest request = new GetTopicRequest();
		request.setUserId(editorUserId);
		request.setTopicId(1L);
		GetWorksTopicResponse response = topicService.getTopic(request);
		assertThat(response.getTopicDto().getTopicId()).isEqualTo(1L);
		assertThat(response.getNovels().size()).isEqualTo(2);
	}

	@Test
	public void getTopicWhenTopicNoExist() {
		GetTopicRequest request = new GetTopicRequest();
		request.setUserId(editorUserId);
		request.setTopicId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			topicService.getTopic(request);
		});
	}

	@Test
	public void addTopicWorksFailedWhenPermissionDeny() {
		GetTopicRequest request = new GetTopicRequest();
		request.setUserId(userId);
		request.setTopicId(1L);
		assertException(PermissionDenyException.class, () -> {
			topicService.getTopic(request);
		});
	}
}
