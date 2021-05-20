package com.qiangdong.reader.user_chat;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserChat;
import com.qiangdong.reader.request.user_chat.DeleteUserChatRequest;
import com.qiangdong.reader.serviceImpl.UserChatServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserChatServiceDeleteUserChatTest extends BaseTest {

    @Autowired
    private UserChatServiceImpl userChatService;

    @Test
    public void deleteUserChatByChatUserIdsSuccessful() {
        DeleteUserChatRequest request = new DeleteUserChatRequest();
        request.setUserId(authorUserId);
        List<Long> chatUserIds = new ArrayList<Long>();
        chatUserIds.add(userId);
        request.setChatUserIds(chatUserIds);
        userChatService.deleteUserChat(request);
        List<UserChat> userChats = userChatService.list(new QueryWrapper<UserChat>()
                .eq("user_id", authorUserId)
                .eq("chat_user_id", userId));
        assertThat(userChats.size()).isEqualTo(0);
    }

    @Test
    public void deleteUserChatByUserChatIdsSuccessful() {
        DeleteUserChatRequest request = new DeleteUserChatRequest();
        request.setUserId(authorUserId);
        List<Long> userChatIds = new ArrayList<Long>();
        userChatIds.add(1L);
        userChatIds.add(3L);
        request.setUserChatIds(userChatIds);
        userChatService.deleteUserChat(request);
        List<UserChat> userChats = userChatService.list(new QueryWrapper<UserChat>()
                .eq("user_id", authorUserId));
        assertThat(userChats.size()).isEqualTo(3);
    }
}
