package com.qiangdong.reader.topic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Topic;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.TopicServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TopicServiceListTopicTest extends BaseTest {
    @Autowired
    private TopicServiceImpl topicService;

    @Test
    public void listSuccessful() {
        PageResponse<Topic> response = topicService.listTopic(new BaseRequest());
        assertThat(response.getList().size()).isEqualTo(3);
    }
}
