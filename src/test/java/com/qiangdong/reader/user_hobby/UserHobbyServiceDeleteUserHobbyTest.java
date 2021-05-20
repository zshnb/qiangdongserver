package com.qiangdong.reader.user_hobby;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserHobby;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_hobby.DeleteUserHobbyRequest;
import com.qiangdong.reader.serviceImpl.UserHobbyServiceImpl;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserHobbyServiceDeleteUserHobbyTest extends BaseTest {

    @Autowired
    UserHobbyServiceImpl userHobbyService;

    @Test
    public void deleteUserHobbySuccessful() {
        DeleteUserHobbyRequest request = new DeleteUserHobbyRequest();
        request.setUserId(authorUserId);
        request.setUserHobbyId(1L);
        userHobbyService.deleteUserHobby(request, new UserHobby());
        BaseRequest listRequest = new BaseRequest();
        listRequest.setUserId(authorUserId);
        List<UserHobby> userHobbys = userHobbyService.listUserHobby(listRequest).getList();
        assertThat(userHobbys.size()).isEqualTo(1);
    }

    @Test
    public void deleteUserHobbyFailedWhenUserHobbyNoExist() {
        DeleteUserHobbyRequest request = new DeleteUserHobbyRequest();
        request.setUserId(userId);
        request.setUserHobbyId(-1L);
        assertException(InvalidArgumentException.class, () -> {
            userHobbyService.deleteUserHobby(request, new UserHobby());
        });
    }
}
