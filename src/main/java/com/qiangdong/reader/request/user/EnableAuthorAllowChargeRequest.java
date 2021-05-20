package com.qiangdong.reader.request.user;

import com.qiangdong.reader.request.BaseRequest;

public class EnableAuthorAllowChargeRequest extends BaseRequest {
    private Long authorId = 0L;

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
