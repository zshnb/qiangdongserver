package com.qiangdong.reader.request.user_goods;

import com.qiangdong.reader.annotation.NotNone;
import com.qiangdong.reader.enums.goods.GoodsTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class BuyUserGoodsRequest extends BaseRequest {
    private Long goodsId = 0L;
    @NotNone(message = "无效的商品类型")
    private GoodsTypeEnum type = GoodsTypeEnum.NONE;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public GoodsTypeEnum getType() {
        return type;
    }

    public void setType(GoodsTypeEnum type) {
        this.type = type;
    }
}
