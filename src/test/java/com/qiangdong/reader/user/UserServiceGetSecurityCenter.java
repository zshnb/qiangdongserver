package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceGetSecurityCenter extends BaseTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void getSecurityCenter() {
        BaseRequest request = new BaseRequest();
        request.setUserId(1L);
        User user = userService.getSecurityCenter(request).getData();
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getMobile()).isEqualTo("137****6404");
        user = userService.getById(1L);
        assertThat(user.getMobile()).isEqualTo("13715166404");
    }
}
