package com.qiangdong.reader.request.user_chat;

import com.qiangdong.reader.enums.common.TopEnum;
import com.qiangdong.reader.request.BaseRequest;

/**
 * @author F
 */
public class UpdateUserChatRequest extends BaseRequest {
    private Long userChatId = 0L;
    private TopEnum top = TopEnum.DEFAULT;
    private String username = "";

    public Long getUserChatId() {
        return userChatId;
    }

    public void setUserChatId(Long userChatId) {
        this.userChatId = userChatId;
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
}
