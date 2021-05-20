package com.qiangdong.reader.request.user_chat;

import com.qiangdong.reader.request.BaseRequest;


public class GetChatDetailHeaderRequest extends BaseRequest {
    private Long chatUserId = 0L;

    public Long getChatUserId() {
        return chatUserId;
    }

    public void setChatUserId(Long chatUserId) {
        this.chatUserId = chatUserId;
    }
}
