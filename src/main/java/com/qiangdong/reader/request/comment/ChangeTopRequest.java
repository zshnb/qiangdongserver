package com.qiangdong.reader.request.comment;

import com.qiangdong.reader.request.BaseRequest;

public class ChangeTopRequest extends BaseRequest {
    private Long commentId = 0L;
    private Boolean top = false;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }
}
