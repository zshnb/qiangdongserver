package com.qiangdong.reader.request.topic;

import com.qiangdong.reader.request.BaseRequest;
import javax.validation.constraints.Size;

public class AddOrUpdateTopicRequest extends BaseRequest {
	private Long topicId = 0L;
    @Size(min = 1, max = 15, message = "话题在1-15字之间")
	private String name = "";
	private String cover = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
}
