package com.qiangdong.reader.request.user;

import com.qiangdong.reader.request.BaseRequest;

public class RewardNovelRequest extends BaseRequest {
    private Long novelId = 0L;

    private Integer coin = 0;

    public Long getNovelId() {
        return novelId;
    }

    public void setNovelId(Long novelId) {
        this.novelId = novelId;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }
}
