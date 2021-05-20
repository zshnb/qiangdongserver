package com.qiangdong.reader.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.qiangdong.reader.enums.common.CommonReadStatusEnum;
import com.qiangdong.reader.enums.common.TopEnum;
import com.qiangdong.reader.enums.user_chat.ChatStatusEnum;
import com.qiangdong.reader.enums.user_chat.ChatTagEnum;
import com.qiangdong.reader.enums.user_chat.ChatTypeEnum;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 用户聊天信息表
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-10
 */
public class UserChat implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 聊天用户
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long chatUserId;

    /**
     * 发送方用户ID
     */
    private Long sender;

    /**
     * 消息类型
     */
    private ChatTypeEnum type;

    /**
     * 消息内容
     */
    private String message;

    private TopEnum top;

    private String username;

    private CommonReadStatusEnum readStatus;

    private ChatTagEnum chatTag;

    private ChatStatusEnum status;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    @TableField(update = "now()")
    private LocalDateTime updateAt;

    /**
     * 逻辑删除
     */
    private Integer deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Long getSender() {
        return sender;
    }

    public CommonReadStatusEnum getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(CommonReadStatusEnum readStatus) {
        this.readStatus = readStatus;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public void setSender(Long sender) {
        this.sender = sender;
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

    @Override
    public String toString() {
        return "UserChat{" +
            "id=" + id +
            ", userId=" + userId +
            ", chatUserId=" + chatUserId +
            ", sender=" + sender +
            ", type=" + type +
            ", message" + message +
            ", createAt=" + createAt +
            ", deleted=" + deleted +
        "}";
    }
}
