package com.qiangdong.reader.topic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.TopicDto;
import com.qiangdong.reader.entity.Topic;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.topic.FollowOrUnFollowTopicRequest;
import com.qiangdong.reader.request.topic.GetTopicRequest;
import com.qiangdong.reader.serviceImpl.TopicServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TopicServiceGetTopicTest extends BaseTest {
	@Autowired
	private TopicServiceImpl topicService;

	@Test
	public void getTopicSuccessful() {
		GetTopicRequest request = new GetTopicRequest();
		request.setUserId(userId);
		request.setTopicId(1L);
		FollowOrUnFollowTopicRequest followOrUnFollowTopicRequest = new FollowOrUnFollowTopicRequest();
		followOrUnFollowTopicRequest.setUserId(userId);
		followOrUnFollowTopicRequest.setTopicId(1L);
		topicService.followTopic(followOrUnFollowTopicRequest, new Topic());

		TopicDto topic = topicService.getTopic(request).getData();
		assertThat(topic.getId()).isEqualTo(1L);
		assertThat(topic.getFollow()).isTrue();
		assertThat(topic.getUsername()).isEqualTo("user2");
	}

	@Test
	public void checkTopicFailedWhenNotExist() {
		GetTopicRequest request = new GetTopicRequest();
		request.setTopicId(-1L);
		assertException(InvalidArgumentException.class, () -> topicService.getTopic(request));
	}
}
