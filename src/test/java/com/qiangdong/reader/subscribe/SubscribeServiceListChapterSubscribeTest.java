package com.qiangdong.reader.subscribe;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.SubscribeDto;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.subscribe.ListWorksChapterSubscribeRequest;
import com.qiangdong.reader.serviceImpl.SubscribeServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class SubscribeServiceListChapterSubscribeTest extends BaseTest {

    @Autowired
    private SubscribeServiceImpl subscribeService;

    @Test
    public void listChapterSubscribeSuccessful() {
        ListWorksChapterSubscribeRequest request = new ListWorksChapterSubscribeRequest();
        request.setUserId(authorUserId);
        request.setPostTime(LocalDateTime.now());
        request.setWorksId(2L);
        request.setWorksType(WorksTypeEnum.NOVEL);
        List<SubscribeDto> data = subscribeService.listChapterSubscribe(request).getList();
        assertThat(data.size()).isEqualTo(2);
        assertThat(data.get(0).getTitle()).isEqualTo("novel chapter 1");
        assertThat(data.get(0).getCount()).isEqualTo(2);
        assertThat(data.get(1).getTitle()).isEqualTo("novel chapter 2");
        assertThat(data.get(1).getCount()).isEqualTo(1);
    }

    @Test
    public void listChapterSubscribeWhenWorksTypeNoExist() {
        ListWorksChapterSubscribeRequest request = new ListWorksChapterSubscribeRequest();
        request.setUserId(authorUserId);
        request.setWorksId(1L);
        request.setWorksType(WorksTypeEnum.NONE);
        assertException(InvalidArgumentException.class, () -> {
            subscribeService.listChapterSubscribe(request);
        });
    }
}
