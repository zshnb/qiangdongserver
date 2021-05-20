package com.qiangdong.reader.user_hobby;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserHobby;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.serviceImpl.UserHobbyServiceImpl;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class UserHobbyServiceListUserHobbyTest extends BaseTest {
    @Autowired
    UserHobbyServiceImpl userHobbyService;

    @Test
    public void listUserHobbySuccessful() {
        BaseRequest request = new BaseRequest();
        request.setUserId(authorUserId);
        List<UserHobby> userHobbys = userHobbyService.listUserHobby(request).getList();
        assertThat(userHobbys.size()).isEqualTo(2);
        assertThat(userHobbys.get(0).getName()).isEqualTo("篮球");
        assertThat(userHobbys.get(1).getName()).isEqualTo("美食");
    }
}
