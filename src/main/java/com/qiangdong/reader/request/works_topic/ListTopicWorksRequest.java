package com.qiangdong.reader.request.works_topic;

import com.qiangdong.reader.request.BaseRequest;

public class ListTopicWorksRequest extends BaseRequest {
    private Long worksTopicId = 0L;

    public Long getWorksTopicId() {
        return worksTopicId;
    }

    public void setWorksTopicId(Long worksTopicId) {
        this.worksTopicId = worksTopicId;
    }
}
