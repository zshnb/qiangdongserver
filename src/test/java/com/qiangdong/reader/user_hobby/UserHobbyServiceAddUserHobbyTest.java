package com.qiangdong.reader.user_hobby;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserHobby;
import com.qiangdong.reader.enums.user_hobby.UserHobbyTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.user_hobby.AddUserHobbyRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.UserHobbyServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserHobbyServiceAddUserHobbyTest extends BaseTest {

    @Autowired
    private UserHobbyServiceImpl userHobbyService;

    @Test
    public void addUserHobbySuccessful() {
        AddUserHobbyRequest request = new AddUserHobbyRequest();
        request.setUserId(1L);
        request.setName("球");
        request.setType(UserHobbyTypeEnum.LOVE);
        Response<UserHobby> response = userHobbyService.addUserHobby(request);
        UserHobby userHobby = userHobbyService.getById(response.getData().getId());
        assertThat(userHobby).isNotNull();
        assertThat(userHobby.getName()).isEqualTo("球");
        assertThat(userHobby.getType()).isEqualTo(UserHobbyTypeEnum.LOVE);
    }

    @Test
    public void addUserHobbyFailedWhenUserHobbyExist() {
        AddUserHobbyRequest request = new AddUserHobbyRequest();
        request.setUserId(1L);
        request.setName("篮球");
        request.setType(UserHobbyTypeEnum.LOVE);
        assertException(InvalidArgumentException.class, () -> {
            userHobbyService.addUserHobby(request);
        });
    }
}
