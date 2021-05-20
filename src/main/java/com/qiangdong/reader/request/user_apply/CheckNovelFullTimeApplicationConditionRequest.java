package com.qiangdong.reader.request.user_apply;


import com.qiangdong.reader.request.BaseRequest;

public class CheckNovelFullTimeApplicationConditionRequest extends BaseRequest {
    /**
     * 全勤奖只有小说
     */
    private Long novelId;

    public Long getNovelId() {
        return novelId;
    }

    public void setNovelId(Long novelId) {
        this.novelId = novelId;
    }
}
