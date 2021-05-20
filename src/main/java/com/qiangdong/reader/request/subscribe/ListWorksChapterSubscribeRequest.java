package com.qiangdong.reader.request.subscribe;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

import java.time.LocalDateTime;

public class ListWorksChapterSubscribeRequest extends BaseRequest {
    private Long worksId = 0L;
    private WorksTypeEnum worksType = WorksTypeEnum.NONE;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime postTime = LocalDateTime.now();

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

    public LocalDateTime getPostTime() {
        return postTime;
    }

    public void setPostTime(LocalDateTime postTime) {
        this.postTime = postTime;
    }
}
