package com.qiangdong.reader.request.novel;

import com.qiangdong.reader.enums.common.RecommendTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class ListRecommendNovelRequest extends BaseRequest {
	private RecommendTypeEnum recommendType = RecommendTypeEnum.NONE;

	public RecommendTypeEnum getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(RecommendTypeEnum recommendType) {
		this.recommendType = recommendType;
	}
}
