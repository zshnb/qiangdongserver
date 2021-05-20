package com.qiangdong.reader.works_topic;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.WorksTopic;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.works_topic.AddTopicWorksRequest;
import com.qiangdong.reader.serviceImpl.WorksTopicServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class WorksTopicServiceAddTopicWorksTest extends BaseTest {
    @Autowired
    private WorksTopicServiceImpl worksTopicService;

    @Test
    public void addTopicWorksSuccessful() {
        AddTopicWorksRequest request = new AddTopicWorksRequest();
        request.setUserId(editorUserId);
        request.setWorksTopicId(1L);
        request.setWorksId(1L);
        request.setWorksType(WorksTypeEnum.NOVEL);
        worksTopicService.addTopicWorks(request, new WorksTopic());
    }

    @Test
    public void addTopicWorksFailedWhenTopicNoExist() {
        AddTopicWorksRequest request = new AddTopicWorksRequest();
        request.setUserId(editorUserId);
        request.setWorksTopicId(-1L);
        request.setWorksId(1L);
        request.setWorksType(WorksTypeEnum.NOVEL);
        assertException(InvalidArgumentException.class, ()->{
            worksTopicService.addTopicWorks(request, new WorksTopic());
        });

    }

    @Test
    public void addTopicWorksFailedWhenWorksNoExist() {
        AddTopicWorksRequest request = new AddTopicWorksRequest();
        request.setUserId(editorUserId);
        request.setWorksTopicId(1L);
        request.setWorksId(-1L);
        request.setWorksType(WorksTypeEnum.COMIC);
        assertException(InvalidArgumentException.class, () -> {
            worksTopicService.addTopicWorks(request, new WorksTopic());
        });
    }

    @Test
    public void addTopicWorksFailedWhenWorksTypeInvalid() {
        AddTopicWorksRequest request = new AddTopicWorksRequest();
        request.setUserId(editorUserId);
        request.setWorksTopicId(1L);
        request.setWorksId(11L);
        request.setWorksType(WorksTypeEnum.NONE);
        assertException(InvalidArgumentException.class, () -> {
            worksTopicService.addTopicWorks(request, new WorksTopic());
        });
    }

    @Test
    public void addTopicWorksFailedWhenPermissionDeny() {
        AddTopicWorksRequest request = new AddTopicWorksRequest();
        request.setUserId(userId);
        request.setWorksTopicId(1L);
        request.setWorksId(11L);
        request.setWorksType(WorksTypeEnum.NONE);
        assertException(PermissionDenyException.class, () -> {
            worksTopicService.addTopicWorks(request, new WorksTopic());
        });
    }
}
