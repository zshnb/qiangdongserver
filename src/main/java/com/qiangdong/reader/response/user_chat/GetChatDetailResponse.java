package com.qiangdong.reader.response.user_chat;

import com.qiangdong.reader.enums.common.TopEnum;
import com.qiangdong.reader.enums.user_chat.ChatTagEnum;

/**
 * @author F
 */
public class GetChatDetailResponse {

    private String username;
    private TopEnum top;
    private ChatTagEnum chatTag;
    private boolean isFollow;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
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
