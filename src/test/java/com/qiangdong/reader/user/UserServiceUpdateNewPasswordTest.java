package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user.UpdateNewPasswordRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

public class UserServiceUpdateNewPasswordTest extends BaseTest {

    @Autowired
    private UserServiceImpl userService;

    /**
     * Expected :e10adc3949ba59abbe56e057f20f883e
     * Actual   :d41d8cd98f00b204e9800998ecf8427e
     */
    @Test
    public void updateNewPasswordSuccessful() {
        String password = "123456";
        UpdateNewPasswordRequest request = new UpdateNewPasswordRequest();
        request.setUserId(editorUserId);
        request.setPassword(password);
        userService.updateNewPassword(request, new User());
        User user = userService.getById(editorUserId);
        assertThat(user.getPassword()).isEqualTo(DigestUtils.md5DigestAsHex(password.getBytes()));
    }

    @Test
    public void updateNewPasswordFailedWhenPasswordExist() {
        UpdateNewPasswordRequest request = new UpdateNewPasswordRequest();
        request.setUserId(userId);
        request.setPassword("123456");
        assertException(InvalidArgumentException.class, () -> {
            userService.updateNewPassword(request, new User());
        });
    }

}
