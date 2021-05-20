package com.qiangdong.reader.request.user;

import com.qiangdong.reader.request.BaseRequest;

public class RewardComicRequest extends BaseRequest {
    private Long comicId = 0L;

    private Integer coin = 0;

    public Long getComicId() {
        return comicId;
    }

    public void setComicId(Long comicId) {
        this.comicId = comicId;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }
}
