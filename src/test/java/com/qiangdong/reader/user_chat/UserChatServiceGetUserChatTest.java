package com.qiangdong.reader.user_chat;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user_chat.UserChatDto;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_chat.GetUserChatRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.UserChatServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserChatServiceGetUserChatTest extends BaseTest {

    @Autowired
    private UserChatServiceImpl userChatService;

    @Test
    public void getUserChatSuccessful() {
        GetUserChatRequest request = new GetUserChatRequest();
        request.setUserId(authorUserId);
        request.setChatUserId(userId);
        PageResponse<UserChatDto> response = userChatService.getUserChat(request, new User());
        assertThat(response.getList().get(0).getMessage()).isEqualTo("理理我");
        assertThat(response.getList().get(1).getMessage()).isEqualTo("HI，很高兴认识你");
        assertThat(response.getList().get(2).getMessage()).isEqualTo("你好");
    }

    @Test
    public void getUserChatFailedWhenUserNoExist() {
        GetUserChatRequest request = new GetUserChatRequest();
        request.setUserId(-1L);
        request.setChatUserId(userId);
        assertException(InvalidArgumentException.class, () -> {
            userChatService.getUserChat(request, new User());
        });
    }
}
