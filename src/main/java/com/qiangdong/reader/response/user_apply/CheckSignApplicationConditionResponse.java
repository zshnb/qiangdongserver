package com.qiangdong.reader.response.user_apply;

public class CheckSignApplicationConditionResponse {

    /**
     * 作品总字数是否超过 5w
     */
    private Boolean isWordCount = false;

    /**
     * 作品是否通过审核
     */
    private Boolean isReview = false;

    /**
     * 是否满足作品申请提交限制
     */
    private Boolean isLastApply = false;

    /**
     * 作品授权类型是否合格
     */
    private Boolean isAuthorization = false;

    public Boolean getWordCount() {
        return isWordCount;
    }

    public void setWordCount(Boolean wordCount) {
        isWordCount = wordCount;
    }

    public Boolean getReview() {
        return isReview;
    }

    public void setReview(Boolean review) {
        isReview = review;
    }

    public Boolean getLastApply() {
        return isLastApply;
    }

    public void setLastApply(Boolean lastApply) {
        isLastApply = lastApply;
    }

    public Boolean getAuthorization() {
        return isAuthorization;
    }

    public void setAuthorization(Boolean authorization) {
        isAuthorization = authorization;
    }
}
