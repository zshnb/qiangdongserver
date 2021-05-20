package com.qiangdong.reader.response.user_apply;


public class CheckRecommendApplicationConditionResponse {
    /**
     * 作者码字是否达标 10w
     */
    private Boolean isWordCount = false;

    /**
     *  作品是否通过审核
     */
    private Boolean isReview = false;

    /**
     * 作品每周只有一次申请机会
     */
    private Boolean isWeekApply = false;

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

    public Boolean getWeekApply() {
        return isWeekApply;
    }

    public void setWeekApply(Boolean weekApply) {
        isWeekApply = weekApply;
    }
}
