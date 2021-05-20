package com.qiangdong.reader.works_topic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.WorksTopic;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.works_topic.AddOrUpdateWorksTopicRequest;
import com.qiangdong.reader.request.works_topic.AddTopicWorksRequest;
import com.qiangdong.reader.serviceImpl.WorksTopicServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorksTopicServiceAddOrUpdateTopicTest extends BaseTest {

	@Autowired
	private WorksTopicServiceImpl topicService;

	@Test
	public void addTopicSuccessful(){
		AddOrUpdateWorksTopicRequest request = new AddOrUpdateWorksTopicRequest();
		request.setUserId(editorUserId);
		request.setName("new Topic");
		request.setDescription("topic desc...");
		request.setTypeId(2L);
		List<Long> worksIds = new ArrayList<Long>();
		worksIds.add(1L);
		request.setWorksIds(worksIds);
		WorksTopic topic = topicService.addOrUpdateTopic(request, new Type()).getData();
		assertThat(topic.getId()).isNotZero();
	}

	@Test
	public void updateTopicSuccessful(){
		AddOrUpdateWorksTopicRequest request = new AddOrUpdateWorksTopicRequest();
		request.setUserId(editorUserId);
		request.setTopicId(1L);
		request.setName("new Topic2");
		request.setTypeId(2L);
		topicService.addOrUpdateTopic(request, new Type());
		WorksTopic topic = topicService.getById(1L);
		assertThat(topic.getName()).isEqualTo("new Topic2");
	}

	@Test
	public void addTopicWhenTypeNoExist(){
		AddOrUpdateWorksTopicRequest request = new AddOrUpdateWorksTopicRequest();
		request.setUserId(editorUserId);
		request.setName("new Topic");
		request.setDescription("topic desc...");
		request.setTypeId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			topicService.addOrUpdateTopic(request, new Type());
		});
	}

	@Test
	public void updateTopicWhenTopicNoExist(){
		AddOrUpdateWorksTopicRequest request = new AddOrUpdateWorksTopicRequest();
		request.setUserId(editorUserId);
		request.setTopicId(-1L);
		request.setName("new Topic2");
		assertException(InvalidArgumentException.class,()->{
			topicService.addOrUpdateTopic(request, new Type());
		});
	}

	@Test
	public void addOrUpdateTopicFailedWhenPermissionDeny() {
		AddOrUpdateWorksTopicRequest request = new AddOrUpdateWorksTopicRequest();
		request.setUserId(userId);
		request.setName("new Topic");
		request.setDescription("topic desc...");
		request.setTypeId(2L);
		assertException(PermissionDenyException.class, () -> {
			topicService.addOrUpdateTopic(request, new Type());
		});
	}
}
