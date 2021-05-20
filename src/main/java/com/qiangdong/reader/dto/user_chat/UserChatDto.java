package com.qiangdong.reader.dto.user_chat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.qiangdong.reader.enums.common.TopEnum;
import com.qiangdong.reader.enums.user.UserChatStatusEnum;
import com.qiangdong.reader.enums.user_chat.ChatTagEnum;
import com.qiangdong.reader.enums.user_chat.ChatTypeEnum;

public class UserChatDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long chatId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long chatUserId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sender;
    private String username;
    private String avatar;
    private UserChatStatusEnum chatStatus;
    private ChatTypeEnum type;
    private String message;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private String createAt;
    private Integer unreadCount;
    private TopEnum top;
    private ChatTagEnum chatTag;

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getChatId() {
        return chatId;
    }
    public Long getChatUserId() {
        return chatUserId;
    }

    public void setChatUserId(Long chatUserId) {
        this.chatUserId = chatUserId;
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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(Integer unreadCount) {
        this.unreadCount = unreadCount;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public UserChatStatusEnum getChatStatus() {
        return chatStatus;
    }

    public void setChatStatus(UserChatStatusEnum chatStatus) {
        this.chatStatus = chatStatus;
    }

    public TopEnum getTop() {
        return top;
    }

    public void setTop(TopEnum top) {
        this.top = top;
    }

    public ChatTagEnum getChatTag() {
        return chatTag;
    }

    public void setChatTag(ChatTagEnum chatTag) {
        this.chatTag = chatTag;
    }
}
