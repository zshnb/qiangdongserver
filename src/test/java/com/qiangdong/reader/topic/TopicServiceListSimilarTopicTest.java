package com.qiangdong.reader.topic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.TopicDto;
import com.qiangdong.reader.entity.Topic;
import com.qiangdong.reader.request.topic.FollowOrUnFollowTopicRequest;
import com.qiangdong.reader.request.topic.ListSimilarTopicRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.TopicServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TopicServiceListSimilarTopicTest extends BaseTest {
    @Autowired
    private TopicServiceImpl topicService;

    @Test
    public void listSuccessful() {
        FollowOrUnFollowTopicRequest followOrUnFollowTopicRequest = new FollowOrUnFollowTopicRequest();
        followOrUnFollowTopicRequest.setUserId(userId);
        followOrUnFollowTopicRequest.setTopicId(2L);
        topicService.followTopic(followOrUnFollowTopicRequest, new Topic());

        ListSimilarTopicRequest request = new ListSimilarTopicRequest();
        request.setUserId(userId);
        request.setName("topic1");
        PageResponse<TopicDto> response = topicService.listSimilarTopic(request);
        assertThat(response.getList().size()).isEqualTo(2);
        assertThat(response.getList().get(0).getFollow()).isTrue();
    }
}
