package com.qiangdong.reader.request.user_apply;

import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class CheckWorksApplicationConditionRequest extends BaseRequest {

    private Long worksId = 0L;
    private WorksTypeEnum type = WorksTypeEnum.NONE;

    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }

    public WorksTypeEnum getType() {
        return type;
    }

    public void setType(WorksTypeEnum type) {
        this.type = type;
    }

}
