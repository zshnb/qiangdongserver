package com.qiangdong.reader.request.user;

import com.qiangdong.reader.enums.user.UserChatStatusEnum;
import com.qiangdong.reader.request.BaseRequest;


public class UpdateUserChatStatusRequest extends BaseRequest {

    private UserChatStatusEnum chatStatus = UserChatStatusEnum.NONE;

    public UserChatStatusEnum getChatStatus() {
        return chatStatus;
    }

    public void setChatStatus(UserChatStatusEnum chatStatus) {
        this.chatStatus = chatStatus;
    }
}
