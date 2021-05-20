package com.qiangdong.reader.user_apply;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_apply.CheckWorksApplicationConditionRequest;
import com.qiangdong.reader.response.user_apply.CheckRecommendApplicationConditionResponse;
import com.qiangdong.reader.serviceImpl.UserApplyServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserApplyServiceCheckRecommendApplicationConditionTest extends BaseTest {

    @Autowired
    private UserApplyServiceImpl applyService;

    @Test
    public void UserApplyCheckNovelRecommendApplicationConditionSuccessful() {
        CheckWorksApplicationConditionRequest request = new CheckWorksApplicationConditionRequest();
        request.setType(WorksTypeEnum.NOVEL);
        request.setWorksId(1L);
        CheckRecommendApplicationConditionResponse response =
                applyService.checkNovelRecommendApplicationCondition(request);
        assertThat(response.getReview()).isTrue();
        assertThat(response.getWordCount()).isTrue();
        assertThat(response.getWeekApply()).isTrue();
    }

    @Test
    public void UserApplyCheckComicRecommendApplicationConditionSuccessful() {
        CheckWorksApplicationConditionRequest request = new CheckWorksApplicationConditionRequest();
        request.setType(WorksTypeEnum.COMIC);
        request.setWorksId(1L);
        CheckRecommendApplicationConditionResponse response =
                applyService.checkComicRecommendApplicationCondition(request);
        assertThat(response.getReview()).isTrue();
        assertThat(response.getWeekApply()).isTrue();
    }

    @Test
    public void UserApplyCheckNovelRecommendApplicationConditionWhenNovelNoExist() {
        CheckWorksApplicationConditionRequest request = new CheckWorksApplicationConditionRequest();
        request.setType(WorksTypeEnum.NOVEL);
        request.setWorksId(100L);
        assertException(InvalidArgumentException.class, () -> {
            applyService.checkNovelRecommendApplicationCondition(request);
        });
    }


    @Test
    public void UserApplyCheckComicRecommendApplicationConditionWhenComicNoExist() {
        CheckWorksApplicationConditionRequest request = new CheckWorksApplicationConditionRequest();
        request.setType(WorksTypeEnum.COMIC);
        request.setWorksId(100L);
        assertException(InvalidArgumentException.class, () -> {
            applyService.checkComicRecommendApplicationCondition(request);
        });
    }
}
