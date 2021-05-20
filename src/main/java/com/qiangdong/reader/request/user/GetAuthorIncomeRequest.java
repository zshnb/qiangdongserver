package com.qiangdong.reader.request.user;

import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

import java.time.LocalDateTime;

public class GetAuthorIncomeRequest extends BaseRequest {
    private Long worksId = 0L;
    private WorksTypeEnum worksType = WorksTypeEnum.NONE;
    private LocalDateTime time = LocalDateTime.now();

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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
