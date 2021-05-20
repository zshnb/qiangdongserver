package com.qiangdong.reader.request.works_topic;


import com.qiangdong.reader.request.BaseRequest;
import java.util.ArrayList;
import java.util.List;

public class AddOrUpdateWorksTopicRequest extends BaseRequest {
	private Long topicId = 0L;
	private Long typeId = 0L;
	private String name = "";
	private String description = "";
	private String cover = "";
	private List<Long> worksIds = new ArrayList<Long>();

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public List<Long> getWorksIds() {
		return worksIds;
	}

	public void setWorksIds(List<Long> worksIds) {
		this.worksIds = worksIds;
	}
}
