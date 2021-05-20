package com.qiangdong.reader.request.goods;

import com.qiangdong.reader.enums.goods.GoodsTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class ListGoodsByTypeRequest extends BaseRequest {
	private GoodsTypeEnum type = GoodsTypeEnum.NONE;

	public GoodsTypeEnum getType() {
		return type;
	}

	public void setType(GoodsTypeEnum type) {
		this.type = type;
	}
}
