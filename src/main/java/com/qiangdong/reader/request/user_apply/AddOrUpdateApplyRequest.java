package com.qiangdong.reader.request.user_apply;


import com.qiangdong.reader.enums.apply.ApplyTypeEnum;
import com.qiangdong.reader.enums.apply.ApplyStatusEnum;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class AddOrUpdateApplyRequest extends BaseRequest {

    private Long applyId = 0L;
    private Long userId = 0L;
    private ApplyTypeEnum applyType = ApplyTypeEnum.NONE;
    private String applyReason = "";
    private ApplyStatusEnum applyStatus = ApplyStatusEnum.NONE;
    private Long worksId = 0L;
    private WorksTypeEnum worksType = WorksTypeEnum.NONE;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public ApplyTypeEnum getApplyType() {
        return applyType;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public void setApplyType(ApplyTypeEnum applyType) {
        this.applyType = applyType;
    }

    public ApplyStatusEnum getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(ApplyStatusEnum applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }

    public WorksTypeEnum getWorksType() {
        return worksType;
    }

    public void setWorksType(WorksTypeEnum worksType) {
        this.worksType = worksType;
    }
}
