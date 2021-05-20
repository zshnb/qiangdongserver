package com.qiangdong.reader.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qiangdong.reader.config.JsonLongSerializer;
import com.qiangdong.reader.enums.message.MessageReadStatusEnum;
import com.qiangdong.reader.enums.message.MessageTypeEnum;

import java.time.LocalDateTime;

public class MessageDto {
	@JsonSerialize(using = JsonLongSerializer.class)
	private Long associateId;
	private Object message;
	private LocalDateTime createAt;
	private MessageReadStatusEnum readStatus;
	private MessageTypeEnum messageType;
	private String image;

	public Long getAssociateId() {
		return associateId;
	}

	public void setAssociateId(Long associateId) {
		this.associateId = associateId;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public MessageReadStatusEnum getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(MessageReadStatusEnum readStatus) {
		this.readStatus = readStatus;
	}

	public MessageTypeEnum getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageTypeEnum messageType) {
		this.messageType = messageType;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
