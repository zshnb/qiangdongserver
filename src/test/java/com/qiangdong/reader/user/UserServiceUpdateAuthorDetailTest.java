package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.user.UpdateAuthorDetailRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceUpdateAuthorDetailTest extends BaseTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void updateAuthorDetailSuccessful(){
        UpdateAuthorDetailRequest request = new UpdateAuthorDetailRequest();
        request.setUserId(authorUserId);
        request.setAddress("sz");
        request.setNickname("知非");
        request.setQqAccount("1408492235");
        request.setMobile("88888888");
        userService.updateAuthorDetail(request, new User());
        User user = userService.getById(authorUserId);
        assertThat(user.getMobile()).isEqualTo(request.getMobile());
        assertThat(user.getNickname()).isEqualTo(request.getNickname());
        assertThat(user.getQqAccount()).isEqualTo(request.getQqAccount());
    }

    @Test
    public void updateAuthorDetailFailedWhenUpdateTimeLimit() {
        UpdateAuthorDetailRequest request = new UpdateAuthorDetailRequest();
        request.setUserId(authorUserId);
        request.setAddress("sz");
        request.setNickname("知非");
        request.setQqAccount("1408492235");
        request.setMobile("88888888");
        userService.updateAuthorDetail(request, new User());
        assertException(InvalidArgumentException.class, () -> userService.updateAuthorDetail(request, new User()));
    }

    @Test
    public void updateAuthorDetailFailedWhenPermissionDeny() {
        UpdateAuthorDetailRequest request = new UpdateAuthorDetailRequest();
        request.setUserId(userId);
        request.setAddress("sz");
        request.setNickname("知非");
        request.setQqAccount("1408492235");
        request.setMobile("88888888");
        assertException(PermissionDenyException.class, () -> userService.updateAuthorDetail(request, new User()));
    }

    @Test
    public void updateAuthorDetailFailedWhenNicknameExist() {
        UpdateAuthorDetailRequest request = new UpdateAuthorDetailRequest();
        request.setUserId(authorUserId);
        request.setAddress("sz");
        request.setNickname("author 2");
        request.setQqAccount("1408492235");
        request.setMobile("88888888");
        assertException(InvalidArgumentException.class, () -> userService.updateAuthorDetail(request, new User()));
    }
}
