package com.qiangdong.reader.dto.user_activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class CreateActivity implements Serializable {
	@Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
	private String content = "";
	/**
	 * 分享数
	 */
	private Integer shareCount = 0;

	/**
	 * 评论数
	 */
	private Integer commentCount = 0;

	/**
	 * 赞同数
	 */
	private Integer agreeCount = 0;

	/**
	 * 反对数
	 */
	private Integer againstCount = 0;

	private List<String> images = new ArrayList<>();

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getAgreeCount() {
		return agreeCount;
	}

	public void setAgreeCount(Integer agreeCount) {
		this.agreeCount = agreeCount;
	}

	public Integer getAgainstCount() {
		return againstCount;
	}

	public void setAgainstCount(Integer againstCount) {
		this.againstCount = againstCount;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}
}
