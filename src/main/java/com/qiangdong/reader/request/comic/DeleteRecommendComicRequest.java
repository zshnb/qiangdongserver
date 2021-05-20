package com.qiangdong.reader.request.comic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qiangdong.reader.annotation.NotNone;
import com.qiangdong.reader.enums.common.RecommendTypeEnum;
import com.qiangdong.reader.request.BaseRequest;
import java.time.LocalDateTime;

public class DeleteRecommendComicRequest extends BaseRequest {
	private Long comicId = 0L;
	@NotNone
	private RecommendTypeEnum recommendType = RecommendTypeEnum.NONE;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createAt = LocalDateTime.now();
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime endAt = LocalDateTime.now();

	public RecommendTypeEnum getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(RecommendTypeEnum recommendType) {
		this.recommendType = recommendType;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public Long getComicId() {
		return comicId;
	}

	public void setComicId(Long comicId) {
		this.comicId = comicId;
	}

	public LocalDateTime getEndAt() {
		return endAt;
	}

	public void setEndAt(LocalDateTime endAt) {
		this.endAt = endAt;
	}
}
