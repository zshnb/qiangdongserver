package com.qiangdong.reader.request.works_topic;

import com.qiangdong.reader.request.BaseRequest;

public class DeleteWorksTopicRequest extends BaseRequest {
	private Long topicId = 0L;

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
}
