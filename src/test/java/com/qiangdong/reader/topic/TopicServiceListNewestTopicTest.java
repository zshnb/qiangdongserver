package com.qiangdong.reader.topic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.TopicDto;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.TopicServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TopicServiceListNewestTopicTest extends BaseTest {
    @Autowired
    private TopicServiceImpl topicService;

    @Test
    public void listSuccessful() {
        PageResponse<TopicDto> response = topicService.listNewestTopic(new BaseRequest());
        assertThat(response.getList().size()).isEqualTo(3);
    }
}
