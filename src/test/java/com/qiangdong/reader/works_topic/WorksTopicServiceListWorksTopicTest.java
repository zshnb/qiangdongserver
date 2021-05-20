package com.qiangdong.reader.works_topic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.WorksTopicDto;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.works_topic.ListWorksTopicRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.WorksTopicServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorksTopicServiceListWorksTopicTest extends BaseTest {

    @Autowired
    private WorksTopicServiceImpl worksTopicService;

    @Test
    public void listWorksTopicSuccessful() {
        ListWorksTopicRequest request = new ListWorksTopicRequest();
        request.setWorksType(WorksTypeEnum.NOVEL);
        PageResponse<WorksTopicDto> response = worksTopicService.listWorksTopic(request);
        assertThat(response.getList().size()).isEqualTo(2);
        assertThat(response.getList().get(0).getTopicId()).isEqualTo(2L);
        assertThat(response.getList().get(0).getName()).isEqualTo("小说专题");
        assertThat(response.getList().get(1).getName()).isEqualTo("new Topic");
    }
}
