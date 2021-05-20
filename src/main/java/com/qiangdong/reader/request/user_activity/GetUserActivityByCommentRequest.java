package com.qiangdong.reader.request.user_activity;

import com.qiangdong.reader.request.BaseRequest;

public class GetUserActivityByCommentRequest extends BaseRequest {
    private Long commentId = 0L;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
}
