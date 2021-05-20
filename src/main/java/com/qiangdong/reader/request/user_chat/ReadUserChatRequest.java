package com.qiangdong.reader.request.user_chat;

import com.qiangdong.reader.request.BaseRequest;

public class ReadUserChatRequest extends BaseRequest {
    private Long userChatId = 0L;

    public Long getUserChatId() {
        return userChatId;
    }

    public void setUserChatId(Long userChatId) {
        this.userChatId = userChatId;
    }
}
