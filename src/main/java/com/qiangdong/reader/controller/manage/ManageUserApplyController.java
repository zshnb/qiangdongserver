package com.qiangdong.reader.controller.manage;


import com.qiangdong.reader.entity.UserApply;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.user_apply.AddOrUpdateApplyRequest;
import com.qiangdong.reader.request.user_apply.CheckNovelFullTimeApplicationConditionRequest;
import com.qiangdong.reader.request.user_apply.CheckWorksApplicationConditionRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.user_apply.CheckRecommendApplicationConditionResponse;
import com.qiangdong.reader.response.user_apply.CheckNovelFullTimeApplicationConditionResponse;
import com.qiangdong.reader.response.user_apply.CheckSignApplicationConditionResponse;
import com.qiangdong.reader.serviceImpl.UserApplyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-16
 */
@RestController
@RequestMapping("/manage/apply")
public class ManageUserApplyController {

    @Autowired
    private UserApplyServiceImpl applyService;

    @PostMapping("/check-sign-status")
    public CheckSignApplicationConditionResponse checkSignApplicationCondition(
            @RequestBody CheckWorksApplicationConditionRequest request) {
        if (WorksTypeEnum.COMIC.equals(request.getType())) {
            return applyService.checkComicSignApplicationCondition(request);
        } else {
            return applyService.checkNovelSignApplicationCondition(request);
        }
    }

    @PostMapping("/check-recommend-status")
    public CheckRecommendApplicationConditionResponse checkRecommendApplicationCondition(
            @RequestBody CheckWorksApplicationConditionRequest request){
        if (WorksTypeEnum.COMIC.equals(request.getType())) {
            return applyService.checkComicRecommendApplicationCondition(request);
        } else {
            return applyService.checkNovelRecommendApplicationCondition(request);
        }
    }

    @PostMapping("/check-full-time-status")
    public CheckNovelFullTimeApplicationConditionResponse checkFullTimeApplicationCondition(
            @RequestBody CheckNovelFullTimeApplicationConditionRequest request){
        return applyService.checkNovelFullTimeApplicationCondition(request);
    }

    @PostMapping("/add-update")
    public Response<UserApply> addOrUpdateApply(@RequestBody AddOrUpdateApplyRequest request){
        return applyService.addOrUpdateApply(request);
    }

}
