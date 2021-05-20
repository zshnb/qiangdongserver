package com.qiangdong.reader.user_chat;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user_chat.UserChatDto;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.user_chat.ChatTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_chat.SendMessageRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.UserChatServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class UserChatServiceSendMessageTest extends BaseTest {

    @Autowired
    private UserChatServiceImpl userChatService;

    @Test
    public void sendMessageSuccessful() {
        SendMessageRequest request = new SendMessageRequest();
        request.setUserId(userId);
        request.setReceiver(authorUserId);
        request.setMessage("Hi,xxx的作者你好");
        request.setType(ChatTypeEnum.TEXT);
        userChatService.sendMessage(request, new User());
        BaseRequest listRequest = new BaseRequest();
        listRequest.setUserId(authorUserId);
        PageResponse<UserChatDto> listResponse = userChatService.listUserChat(listRequest);
        assertThat(listResponse.getList().get(0).getChatUserId()).isEqualTo(userId);
        assertThat(listResponse.getList().get(0).getMessage()).isEqualTo("理理我");
    }

    @Test
    public void sendMessageFailedWhenSenderNoExist() {
        SendMessageRequest request = new SendMessageRequest();
        request.setUserId(-1L);
        request.setReceiver(authorUserId);
        request.setMessage("Hi,xxx的作者你好");
        request.setType(ChatTypeEnum.TEXT);
        assertException(InvalidArgumentException.class, () -> {
            userChatService.sendMessage(request, new User());
        });
    }

    @Test
    public void sendMessageFailedWhenReceiverNoExist() {
        SendMessageRequest request = new SendMessageRequest();
        request.setUserId(userId);
        request.setReceiver(-1L);
        request.setMessage("Hi,xxx的作者你好");
        request.setType(ChatTypeEnum.TEXT);
        assertException(InvalidArgumentException.class, () -> {
            userChatService.sendMessage(request, new User());
        });
    }
}
