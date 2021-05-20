package com.qiangdong.reader.topic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.Topic;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.topic.ListHeatUserActivityRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.TopicServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TopicServiceListHeatUserActivityTest extends BaseTest {
    @Autowired
    private TopicServiceImpl topicService;

    @Test
    public void listSuccessful() {
        ListHeatUserActivityRequest request = new ListHeatUserActivityRequest();
        request.setTopicId(1L);
        PageResponse<UserActivityDto> response = topicService.listHeatUserActivity(request, new Topic());
        assertThat(response.getList().size()).isEqualTo(2);
    }

    @Test
    public void listFailedWhenTopicNotExist() {
        ListHeatUserActivityRequest request = new ListHeatUserActivityRequest();
        request.setTopicId(-1L);
        assertException(InvalidArgumentException.class, () -> topicService.listHeatUserActivity(request, new Topic()));
    }
}
