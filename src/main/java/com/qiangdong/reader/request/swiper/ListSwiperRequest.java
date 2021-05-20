package com.qiangdong.reader.request.swiper;

import com.qiangdong.reader.enums.type.TypeBelongEnum;
import com.qiangdong.reader.request.BaseRequest;

public class ListSwiperRequest extends BaseRequest {
	private TypeBelongEnum typeBelong = TypeBelongEnum.NONE;

	public TypeBelongEnum getTypeBelong() {
		return typeBelong;
	}

	public void setTypeBelong(TypeBelongEnum typeBelong) {
		this.typeBelong = typeBelong;
	}
}
