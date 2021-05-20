package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user.ListUserRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceListUserTest extends BaseTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void listUserSuccessful(){
        ListUserRequest request = new ListUserRequest();
        request.setUserId(5L);
        PageResponse<User> response = userService.listUser(request);
        assertThat(response.getList().size()).isEqualTo(3);
        assertThat(response.getList().get(0).getUsername()).isEqualTo("user1");
        assertThat(response.getList().get(1).getUsername()).isEqualTo("user2");
        assertThat(response.getList().get(2).getUsername()).isEqualTo("user3");
    }
}
