package com.qiangdong.reader.request.comic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qiangdong.reader.annotation.NotNone;
import com.qiangdong.reader.enums.common.RecommendTypeEnum;
import com.qiangdong.reader.request.BaseRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AddRecommendComicRequest extends BaseRequest {
	private List<Long> comicIds = new ArrayList<>();
	@NotNone(message = "无效的推荐类型")
	private RecommendTypeEnum recommendType = RecommendTypeEnum.NONE;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createAt = LocalDateTime.now();
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime endAt = LocalDateTime.now();

	public List<Long> getComicIds() {
		return comicIds;
	}

	public void setComicIds(List<Long> comicIds) {
		this.comicIds = comicIds;
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
