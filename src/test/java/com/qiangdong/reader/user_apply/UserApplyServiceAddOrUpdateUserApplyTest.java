package com.qiangdong.reader.user_apply;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserApply;
import com.qiangdong.reader.enums.apply.ApplyTypeEnum;
import com.qiangdong.reader.enums.apply.ApplyStatusEnum;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_apply.AddOrUpdateApplyRequest;
import com.qiangdong.reader.serviceImpl.UserApplyServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserApplyServiceAddOrUpdateUserApplyTest extends BaseTest {


    @Autowired
    private UserApplyServiceImpl applyService;

    @Test
    public void addUserApplySuccessful(){
        AddOrUpdateApplyRequest request = new AddOrUpdateApplyRequest();
        request.setUserId(1L);
        request.setApplyType(ApplyTypeEnum.FULL_TIME);
        request.setApplyReason("申请全勤奖");
        request.setWorksId(1L);
        request.setWorksType(WorksTypeEnum.NOVEL);
        request.setApplyStatus(ApplyStatusEnum.PENDING);
        UserApply apply = applyService.addOrUpdateApply(request).getData();
        assertThat(apply.getId()).isNotZero();
    }

    @Test
    public void updateUserApplySuccessful(){
        AddOrUpdateApplyRequest request = new AddOrUpdateApplyRequest();
        request.setUserId(1L);
        request.setApplyId(1L);
        request.setApplyStatus(ApplyStatusEnum.PASS);
        applyService.addOrUpdateApply(request).getData();
        UserApply result = applyService.getById(request.getApplyId());
        Assert.assertEquals(ApplyStatusEnum.PASS, result.getApplyStatus());
    }


    @Test
    public void addOrUpdateUserApplyWhenWorksNoExist(){
        AddOrUpdateApplyRequest request = new AddOrUpdateApplyRequest();
        request.setUserId(1L);
        request.setApplyType(ApplyTypeEnum.FULL_TIME);
        request.setApplyReason("申请全勤奖");
        request.setWorksId(100L);
        request.setWorksType(WorksTypeEnum.NOVEL);
        request.setApplyStatus(ApplyStatusEnum.PENDING);
        assertException(InvalidArgumentException.class , () -> {
            applyService.addOrUpdateApply(request).getData();
        });
    }

    @Test
    public void addOrUpdateUserApplyWhenUserNoExist(){
        AddOrUpdateApplyRequest request = new AddOrUpdateApplyRequest();
        request.setUserId(100L);
        request.setApplyType(ApplyTypeEnum.FULL_TIME);
        request.setApplyReason("申请全勤奖");
        request.setWorksId(1L);
        request.setWorksType(WorksTypeEnum.NOVEL);
        request.setApplyStatus(ApplyStatusEnum.PENDING);
        assertException(InvalidArgumentException.class , () -> {
            applyService.addOrUpdateApply(request).getData();
        });
    }



}
