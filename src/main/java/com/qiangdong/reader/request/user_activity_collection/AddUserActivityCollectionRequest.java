package com.qiangdong.reader.request.user_activity_collection;

import com.qiangdong.reader.request.BaseRequest;

public class AddUserActivityCollectionRequest extends BaseRequest {
    private Long userActivityId = 0L;

    public Long getUserActivityId() {
        return userActivityId;
    }

    public void setUserActivityId(Long userActivityId) {
        this.userActivityId = userActivityId;
    }
}
