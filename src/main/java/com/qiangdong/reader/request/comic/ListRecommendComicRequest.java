package com.qiangdong.reader.request.comic;

import com.qiangdong.reader.enums.common.RecommendTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class ListRecommendComicRequest extends BaseRequest {
	private RecommendTypeEnum recommendType = RecommendTypeEnum.NONE;

	public RecommendTypeEnum getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(RecommendTypeEnum recommendType) {
		this.recommendType = recommendType;
	}
}
