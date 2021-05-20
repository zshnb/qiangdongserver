package com.qiangdong.reader.request.novel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qiangdong.reader.annotation.NotNone;
import com.qiangdong.reader.enums.common.RecommendTypeEnum;
import com.qiangdong.reader.request.BaseRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AddRecommendNovelRequest extends BaseRequest {
	private List<Long> novelIds = new ArrayList<>();
	@NotNone
	private RecommendTypeEnum recommendType = RecommendTypeEnum.NONE;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createAt = LocalDateTime.now();
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime endAt = LocalDateTime.now();

	public List<Long> getNovelIds() {
		return novelIds;
	}

	public void setNovelIds(List<Long> novelIds) {
		this.novelIds = novelIds;
	}

	public RecommendTypeEnum getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(RecommendTypeEnum recommendType) {
		this.recommendType = recommendType;
	}

	public LocalDateTime getEndAt() {
		return endAt;
	}

	public void setEndAt(LocalDateTime endAt) {
		this.endAt = endAt;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}
}
