package com.qiangdong.reader.user_chat;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserChat;
import com.qiangdong.reader.enums.common.CommonReadStatusEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_chat.ReadUserChatRequest;
import com.qiangdong.reader.serviceImpl.UserChatServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserChatServiceReadUserChatTest extends BaseTest {

    @Autowired
    private UserChatServiceImpl userChatService;

    @Test
    public void readUserChatSuccessful() {
        ReadUserChatRequest request = new ReadUserChatRequest();
        request.setUserId(userId);
        request.setUserChatId(10L);
        userChatService.readUserChat(request, new UserChat());
        UserChat userChat = userChatService.getById(10L);
        assertThat(userChat.getReadStatus()).isEqualTo(CommonReadStatusEnum.READ);
    }

    @Test
    public void readUserChatFailedWhenChatNoExist() {
        ReadUserChatRequest request = new ReadUserChatRequest();
        request.setUserId(userId);
        request.setUserChatId(-1L);
        assertException(InvalidArgumentException.class, () -> {
            userChatService.readUserChat(request, new UserChat());
        });
    }
}
