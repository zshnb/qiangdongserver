package com.qiangdong.reader.request.topic;

import com.qiangdong.reader.request.BaseRequest;

public class FollowOrUnFollowTopicRequest extends BaseRequest {
    private Long topicId = 0L;

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }
}
