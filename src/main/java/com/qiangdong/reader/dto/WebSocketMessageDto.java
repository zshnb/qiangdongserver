package com.qiangdong.reader.dto;


import com.qiangdong.reader.enums.user_chat.ChatTypeEnum;
import com.qiangdong.reader.enums.user_chat.MessageTypeEnum;

public class WebSocketMessageDto {
    private String id;
    private String userId;
    private String chatUserId;
    private String sender;
    private ChatTypeEnum type;
    private String message;
    private String username;
    private MessageTypeEnum messageType;
    private String avatar;  //消息拥有者的头像

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChatUserId() {
        return chatUserId;
    }

    public void setChatUserId(String chatUserId) {
        this.chatUserId = chatUserId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MessageTypeEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypeEnum messageType) {
        this.messageType = messageType;
    }
}
