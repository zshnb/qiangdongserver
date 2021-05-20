package com.qiangdong.reader.request.user_chat;

import com.qiangdong.reader.enums.common.TopEnum;
import com.qiangdong.reader.enums.user_chat.ChatStatusEnum;
import com.qiangdong.reader.enums.user_chat.ChatTagEnum;
import com.qiangdong.reader.enums.user_chat.ChatTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class SendMessageRequest extends BaseRequest {
    private Long receiver = 0L;
    private ChatTypeEnum type = ChatTypeEnum.TEXT;
    private String message = "";
    private TopEnum top = TopEnum.DEFAULT;
    private String username = "";
    private ChatTagEnum chatTag = ChatTagEnum.NONE;
    private ChatStatusEnum status = ChatStatusEnum.NONE;

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public ChatTypeEnum getType() {
        return type;
    }

    public void setType(ChatTypeEnum type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TopEnum getTop() {
        return top;
    }

    public void setTop(TopEnum top) {
        this.top = top;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ChatTagEnum getChatTag() {
        return chatTag;
    }

    public void setChatTag(ChatTagEnum chatTag) {
        this.chatTag = chatTag;
    }

    public ChatStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ChatStatusEnum status) {
        this.status = status;
    }
}
