package com.qiangdong.reader.request.works_topic;

import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class AddTopicWorksRequest extends BaseRequest {
    private Long worksTopicId = 0L;
    private Long worksId = 0L;
    private WorksTypeEnum worksType = WorksTypeEnum.NONE;

    public Long getWorksTopicId() {
        return worksTopicId;
    }

    public void setWorksTopicId(Long worksTopicId) {
        this.worksTopicId = worksTopicId;
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
