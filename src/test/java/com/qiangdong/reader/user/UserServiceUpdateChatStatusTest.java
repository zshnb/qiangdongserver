package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.user.UserChatStatusEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.UpdateUserChatStatusRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class UserServiceUpdateChatStatusTest extends BaseTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void updateChatStatusSuccessful() {
        UpdateUserChatStatusRequest request = new UpdateUserChatStatusRequest();
        request.setUserId(userId);
        request.setChatStatus(UserChatStatusEnum.OFFLINE);
        userService.updateChatStatus(request, new User());
        User user = userService.getById(userId);
        assertThat(user.getChatStatus()).isEqualTo(UserChatStatusEnum.OFFLINE);
    }

    @Test
    public void updateChatStatusFailedWhenUserNoExist() {
        UpdateUserChatStatusRequest request = new UpdateUserChatStatusRequest();
        request.setUserId(-1L);
        request.setChatStatus(UserChatStatusEnum.OFFLINE);
        assertException(InvalidArgumentException.class, () -> {
            userService.updateChatStatus(request, new User());
        });
    }
}
