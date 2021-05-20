package com.qiangdong.reader.topic;

import static org.mockito.ArgumentMatchers.any;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Topic;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.topic.AddOrUpdateTopicRequest;
import com.qiangdong.reader.serviceImpl.TopicServiceImpl;
import com.qiangdong.reader.utils.TopicUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

public class TopicServiceAddOrUpdateTopicTest extends BaseTest {
	@Autowired
	private TopicServiceImpl topicService;

	@SpyBean
	private TopicUtil topicUtil;

	@Before
	public void beforeMock() {
		Mockito.doNothing().when(topicUtil).indexTopic(any());
	}

	@Test
	public void addTopicSuccessful() {
		AddOrUpdateTopicRequest request = new AddOrUpdateTopicRequest();
		request.setName("name");
		Topic topic = topicService.addOrUpdateTopic(request).getData();
		assertThat(topic.getId()).isNotZero();
	}

	@Test
	public void addTopicFailedWhenDuplicateName() {
		AddOrUpdateTopicRequest request = new AddOrUpdateTopicRequest();
		request.setName("topic1");
		assertException(InvalidArgumentException.class, () -> topicService.addOrUpdateTopic(request));
	}
}
