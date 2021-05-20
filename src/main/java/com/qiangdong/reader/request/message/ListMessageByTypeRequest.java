package com.qiangdong.reader.request.message;

import com.qiangdong.reader.enums.message.MessageTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class ListMessageByTypeRequest extends BaseRequest {
	private MessageTypeEnum messageType = MessageTypeEnum.NONE;

	public MessageTypeEnum getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageTypeEnum messageType) {
		this.messageType = messageType;
	}
}
