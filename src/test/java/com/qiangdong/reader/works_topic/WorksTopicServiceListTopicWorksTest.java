package com.qiangdong.reader.works_topic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.WorksTopic;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.works_topic.ListTopicWorksRequest;
import com.qiangdong.reader.response.works_topic.ListWorksTopicWorksResponse;
import com.qiangdong.reader.serviceImpl.WorksTopicServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorksTopicServiceListTopicWorksTest extends BaseTest {
    @Autowired
    private WorksTopicServiceImpl worksTopicService;

    @Test
    public void listTopicWorksSuccessful() {
        ListTopicWorksRequest request = new ListTopicWorksRequest();
        request.setWorksTopicId(1L);
        ListWorksTopicWorksResponse response = worksTopicService.listTopicWorks(request, new WorksTopic());
        assertThat(response.getTopicDto().getName()).isEqualTo("new Topic");
        assertThat(response.getNovels().size()).isEqualTo(2);
        assertThat(response.getNovels().get(0).getName()).isEqualTo("novel1");
        assertThat(response.getNovels().get(1).getName()).isEqualTo("novel2");
    }

    @Test
    public void listTopicWorksWhenWorksTopicNoExist(){
        ListTopicWorksRequest request = new ListTopicWorksRequest();
        request.setWorksTopicId(-1L);
        assertException(InvalidArgumentException.class, () -> {
            worksTopicService.listTopicWorks(request, new WorksTopic());
        });
    }
}
