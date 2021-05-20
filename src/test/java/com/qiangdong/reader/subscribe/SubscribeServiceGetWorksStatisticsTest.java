package com.qiangdong.reader.subscribe;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.subscribe.GetWorksStatisticsRequest;
import com.qiangdong.reader.response.subscribe.WorksStatisticsResponse;
import com.qiangdong.reader.serviceImpl.SubscribeServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SubscribeServiceGetWorksStatisticsTest extends BaseTest {

    @Autowired
    private SubscribeServiceImpl subscribeService;

    @Test
    public void getWorksStatisticsSuccessful() {
        GetWorksStatisticsRequest request = new GetWorksStatisticsRequest();
        request.setUserId(authorUserId);
        request.setWorksId(2L);
        request.setWorksType(WorksTypeEnum.NOVEL);
        WorksStatisticsResponse response = subscribeService.getWorksStatistics(request);
        assertThat(response.getCollectCount()).isEqualTo(0);
        assertThat(response.getSubscribeCount()).isEqualTo(3);
        assertThat(response.getMaxChapterSubscribeCount()).isEqualTo(2);
        assertThat(response.getAverageChapterSubscribeCount()).isEqualTo(1);
    }


    @Test
    public void getWorksStatisticsWhenWorksTypeInvalid() {
        GetWorksStatisticsRequest request = new GetWorksStatisticsRequest();
        request.setUserId(authorUserId);
        request.setWorksId(2L);
        request.setWorksType(WorksTypeEnum.NONE);
        assertException(InvalidArgumentException.class, () -> {
            subscribeService.getWorksStatistics(request);
        });
    }

    @Test
    public void getWorksStatisticsWhenWorksNoExist() {
        GetWorksStatisticsRequest request = new GetWorksStatisticsRequest();
        request.setUserId(authorUserId);
        request.setWorksId(-1L);
        request.setWorksType(WorksTypeEnum.NOVEL);
        assertException(InvalidArgumentException.class, () -> {
            subscribeService.getWorksStatistics(request);
        });
    }
}
