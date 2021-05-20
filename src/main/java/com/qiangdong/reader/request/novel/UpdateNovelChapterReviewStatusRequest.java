package com.qiangdong.reader.request.novel;

import com.qiangdong.reader.enums.common.WorksChapterReviewStatusEnum;
import com.qiangdong.reader.request.BaseRequest;

public class UpdateNovelChapterReviewStatusRequest extends BaseRequest {

    private Long chapterId = 0L;

    private WorksChapterReviewStatusEnum reviewStatus = WorksChapterReviewStatusEnum.NONE;

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public WorksChapterReviewStatusEnum getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(WorksChapterReviewStatusEnum reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

}
