package com.qiangdong.reader.request.comment;

import com.qiangdong.reader.enums.message.MessageTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class MentionRequest extends BaseRequest {
	private Long targetUserId = 0L;
	private Long associateId = 0L;
	private MessageTypeEnum type = MessageTypeEnum.NONE;

	public Long getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}

	public Long getAssociateId() {
		return associateId;
	}

	public void setAssociateId(Long associateId) {
		this.associateId = associateId;
	}

	public MessageTypeEnum getType() {
		return type;
	}

	public void setType(MessageTypeEnum type) {
		this.type = type;
	}
}
