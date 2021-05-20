package com.qiangdong.reader.response.user;

import com.qiangdong.reader.dto.RewardDto;
import com.qiangdong.reader.dto.SubscribeDto;

import java.math.BigDecimal;
import java.util.List;

public class GetAuthorIncomeResponse {
    private BigDecimal subscribeCount;
    private BigDecimal rewardCount;
    private List<RewardDto> rewardDtos;
    private List<SubscribeDto> subscribeDtos;

    public BigDecimal getSubscribeCount() {
        return subscribeCount;
    }

    public void setSubscribeCount(BigDecimal subscribeCount) {
        this.subscribeCount = subscribeCount;
    }

    public BigDecimal getRewardCount() {
        return rewardCount;
    }

    public void setRewardCount(BigDecimal rewardCount) {
        this.rewardCount = rewardCount;
    }

    public List<RewardDto> getRewardDtos() {
        return rewardDtos;
    }

    public void setRewardDtos(List<RewardDto> rewardDtos) {
        this.rewardDtos = rewardDtos;
    }

    public List<SubscribeDto> getSubscribeDtos() {
        return subscribeDtos;
    }

    public void setSubscribeDtos(List<SubscribeDto> subscribeDtos) {
        this.subscribeDtos = subscribeDtos;
    }
}
