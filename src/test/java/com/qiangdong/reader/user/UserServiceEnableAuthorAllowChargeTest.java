package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.user.EnableAuthorAllowChargeRequest;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceEnableAuthorAllowChargeTest extends BaseTest {
    @Autowired
    private UserServiceImpl userService;

    @Test
    public void enableSuccessful() {
        EnableAuthorAllowChargeRequest request = new EnableAuthorAllowChargeRequest();
        request.setUserId(editorUserId);
        request.setAuthorId(authorUserId);
        userService.enableAuthorAllowCharge(request);

        User user = userService.getById(authorUserId);
        assertThat(user.getAllowCharge()).isTrue();
    }

    @Test
    public void enableFailedWhenPermissionDeny() {
        EnableAuthorAllowChargeRequest request = new EnableAuthorAllowChargeRequest();
        request.setUserId(userId);
        request.setAuthorId(authorUserId);
        assertException(PermissionDenyException.class, () -> userService.enableAuthorAllowCharge(request));
    }
}
