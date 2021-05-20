package com.qiangdong.reader.user_apply;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_apply.CheckWorksApplicationConditionRequest;
import com.qiangdong.reader.response.user_apply.CheckSignApplicationConditionResponse;
import com.qiangdong.reader.serviceImpl.UserApplyServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserApplyServiceCheckSignApplicationConditionTest extends BaseTest {

    @Autowired
    private UserApplyServiceImpl applyService;

    @Test
    public void UserApplyCheckNovelSignApplicationConditionSuccessful() {
        CheckWorksApplicationConditionRequest request = new CheckWorksApplicationConditionRequest();
        request.setWorksId(1L);
        request.setType(WorksTypeEnum.NOVEL);
        CheckSignApplicationConditionResponse response = applyService.checkNovelSignApplicationCondition(request);
        assertThat(response.getReview()).isTrue();
        assertThat(response.getAuthorization()).isTrue();
    }

    @Test
    public void UserApplyCheckComicSignApplicationConditionSuccessful() {
        CheckWorksApplicationConditionRequest request = new CheckWorksApplicationConditionRequest();
        request.setWorksId(1L);
        request.setType(WorksTypeEnum.COMIC);
        CheckSignApplicationConditionResponse response = applyService.checkComicSignApplicationCondition(request);
        assertThat(response.getAuthorization()).isTrue();
    }

    @Test
    public void UserApplyCheckSignApplicationConditionWhenNovelNoExist() {
        CheckWorksApplicationConditionRequest request = new CheckWorksApplicationConditionRequest();
        request.setWorksId(100L);
        request.setType(WorksTypeEnum.NOVEL);
        assertException(InvalidArgumentException.class, () -> {
            applyService.checkNovelSignApplicationCondition(request);
        });
    }

    @Test
    public void UserApplyCheckSignApplicationConditionWheComicNoExist() {
        CheckWorksApplicationConditionRequest request = new CheckWorksApplicationConditionRequest();
        request.setWorksId(100L);
        request.setType(WorksTypeEnum.COMIC);
        assertException(InvalidArgumentException.class, () -> {
            applyService.checkComicSignApplicationCondition(request);
        });
    }


}
