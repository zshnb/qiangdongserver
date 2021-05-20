package com.qiangdong.reader.user_apply;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_apply.CheckNovelFullTimeApplicationConditionRequest;
import com.qiangdong.reader.response.user_apply.CheckNovelFullTimeApplicationConditionResponse;
import com.qiangdong.reader.serviceImpl.UserApplyServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserApplyServiceCheckNovelFullTimeApplicationConditionTest extends BaseTest {

    @Autowired
    private UserApplyServiceImpl applyService;

    @Test
    public void checkNovelFullTimeApplicationConditionStatusSuccessful() {
        CheckNovelFullTimeApplicationConditionRequest request = new CheckNovelFullTimeApplicationConditionRequest();
        request.setNovelId(1L);
        CheckNovelFullTimeApplicationConditionResponse response =
                applyService.checkNovelFullTimeApplicationCondition(request);
        assertThat(response.getDayWordCount()).isNotTrue();
        assertThat(response.getLevel()).isTrue();
        assertThat(response.getMonthApply()).isTrue();
    }

    @Test
    public void userApplyCheckNovelFullTimeApplicationConditionWhenNovelNoExist() {
        CheckNovelFullTimeApplicationConditionRequest request = new CheckNovelFullTimeApplicationConditionRequest();
        request.setNovelId(100L);
        assertException(InvalidArgumentException.class, () -> {
            applyService.checkNovelFullTimeApplicationCondition(request);
        });
    }

}
