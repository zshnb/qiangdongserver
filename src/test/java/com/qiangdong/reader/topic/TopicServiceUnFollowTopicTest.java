package com.qiangdong.reader.topic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Topic;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.topic.FollowOrUnFollowTopicRequest;
import com.qiangdong.reader.serviceImpl.TopicServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TopicServiceUnFollowTopicTest extends BaseTest {
    @Autowired
    private TopicServiceImpl topicService;

    @Test
    public void unFollowFailedWhenAlreadyFollow() {
        FollowOrUnFollowTopicRequest followOrUnFollowTopicRequest = new FollowOrUnFollowTopicRequest();
        followOrUnFollowTopicRequest.setUserId(userId);
        followOrUnFollowTopicRequest.setTopicId(1L);
        assertException(InvalidArgumentException.class, () ->
            topicService.unFollowTopic(followOrUnFollowTopicRequest, new Topic()));
    }

    @Test
    public void unFollowFailedWhenTopicNotExist() {
        FollowOrUnFollowTopicRequest request = new FollowOrUnFollowTopicRequest();
        request.setTopicId(-1L);
        assertException(InvalidArgumentException.class, () -> topicService.unFollowTopic(request, new Topic()));
    }
}
