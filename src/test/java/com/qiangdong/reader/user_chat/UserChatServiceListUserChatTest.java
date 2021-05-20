package com.qiangdong.reader.user_chat;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user_chat.UserChatDto;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.UserChatServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserChatServiceListUserChatTest extends BaseTest {
    @Autowired
    private UserChatServiceImpl userChatService;

    @Test
    public void listUserChatSuccessful() {
        BaseRequest request = new BaseRequest();
        request.setUserId(authorUserId);
        request.setPageSize(9999L);
        PageResponse<UserChatDto> response = userChatService.listUserChat(request);
        assertThat(response.getList().size()).isEqualTo(2);
        assertThat(response.getList().get(0).getMessage()).isEqualTo("理理我");
        assertThat(response.getList().get(0).getUnreadCount()).isEqualTo(2);
        assertThat(response.getList().get(1).getMessage()).isEqualTo("hellow");
        assertThat(response.getList().get(1).getUnreadCount()).isEqualTo(1);
    }
}
