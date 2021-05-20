package com.qiangdong.reader.request.user_chat;

import com.qiangdong.reader.request.BaseRequest;

import java.util.ArrayList;
import java.util.List;

public class DeleteUserChatRequest extends BaseRequest {
    private List<Long> chatUserIds = new ArrayList<>();
    private List<Long> userChatIds = new ArrayList<>();

    public List<Long> getChatUserIds() {
        return chatUserIds;
    }

    public void setChatUserIds(List<Long> chatUserIds) {
        this.chatUserIds = chatUserIds;
    }

    public List<Long> getUserChatIds() {
        return userChatIds;
    }

    public void setUserChatIds(List<Long> userChatIds) {
        this.userChatIds = userChatIds;
    }
}
