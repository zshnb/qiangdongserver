package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.user.GetAuthorCompletionResponse;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceGetAuthorCompletionTest extends BaseTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void getAuthorComplection() {
        BaseRequest request = new BaseRequest();
        request.setUserId(authorUserId);
        GetAuthorCompletionResponse response = userService.getAuthorCompletion(request, new User());
        assertThat(response.getAuthentication()).isEqualTo(false);
        assertThat(response.getAuthorCompletion()).isEqualTo(25);
    }
}
