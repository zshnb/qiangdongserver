package com.qiangdong.reader.service;

import com.qiangdong.reader.entity.UserApply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.user_apply.AddOrUpdateApplyRequest;
import com.qiangdong.reader.request.user_apply.CheckNovelFullTimeApplicationConditionRequest;
import com.qiangdong.reader.request.user_apply.CheckWorksApplicationConditionRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.user_apply.CheckRecommendApplicationConditionResponse;
import com.qiangdong.reader.response.user_apply.CheckNovelFullTimeApplicationConditionResponse;
import com.qiangdong.reader.response.user_apply.CheckSignApplicationConditionResponse;

public interface IUserApplyService extends IService<UserApply> {

    Response<UserApply> addOrUpdateApply(AddOrUpdateApplyRequest request);

    CheckSignApplicationConditionResponse checkNovelSignApplicationCondition(
            CheckWorksApplicationConditionRequest request);

    CheckSignApplicationConditionResponse checkComicSignApplicationCondition(
            CheckWorksApplicationConditionRequest request);

    CheckRecommendApplicationConditionResponse checkNovelRecommendApplicationCondition(
            CheckWorksApplicationConditionRequest request);

    CheckRecommendApplicationConditionResponse checkComicRecommendApplicationCondition(
            CheckWorksApplicationConditionRequest request);

    CheckNovelFullTimeApplicationConditionResponse checkNovelFullTimeApplicationCondition(
            CheckNovelFullTimeApplicationConditionRequest request);

}
