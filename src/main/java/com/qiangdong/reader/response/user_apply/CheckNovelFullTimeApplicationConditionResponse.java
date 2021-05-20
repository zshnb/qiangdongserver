package com.qiangdong.reader.response.user_apply;


public class CheckNovelFullTimeApplicationConditionResponse {

    /**
     * 作者等级是否满足 LV1
     */
    private Boolean isLevel = false;

    /**
     * 作者每月每天的码字是否达到 4000
     */
    private Boolean isDayWordCount = false;

    /**
     * 作品是否是 VIP 作品
     */
    private Boolean isVip = false;

    /**
     * 每个月只有一次作品全勤申请的机会
     */
    private Boolean isMonthApply = false;


    public Boolean getLevel() {
        return isLevel;
    }

    public void setLevel(Boolean level) {
        isLevel = level;
    }

    public Boolean getDayWordCount() {
        return isDayWordCount;
    }

    public void setDayWordCount(Boolean dayWordCount) {
        isDayWordCount = dayWordCount;
    }

    public Boolean getVip() {
        return isVip;
    }

    public void setVip(Boolean vip) {
        isVip = vip;
    }

    public Boolean getMonthApply() {
        return isMonthApply;
    }

    public void setMonthApply(Boolean monthApply) {
        isMonthApply = monthApply;
    }
}
