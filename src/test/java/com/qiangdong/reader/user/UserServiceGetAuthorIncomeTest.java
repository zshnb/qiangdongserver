package com.qiangdong.reader.user;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.user.GetAuthorIncomeRequest;
import com.qiangdong.reader.response.user.GetAuthorIncomeResponse;
import com.qiangdong.reader.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @author F
 */
public class UserServiceGetAuthorIncomeTest extends BaseTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void getAuthorIncomeSuccessful() {
        GetAuthorIncomeRequest request = new GetAuthorIncomeRequest();
        request.setUserId(authorUserId);
        request.setWorksId(1L);
        request.setWorksType(WorksTypeEnum.NOVEL);
        request.setTime(LocalDateTime.parse("2020-07-01T00:00:00"));
        GetAuthorIncomeResponse response = userService.getAuthorIncome(request);
        assertThat(response.getSubscribeCount().doubleValue()).isEqualTo(0.3);
        assertThat(response.getRewardCount().doubleValue()).isEqualTo(10.0);
        assertThat(response.getRewardDtos().get(0).getUsername()).isEqualTo("user3");
        assertThat(response.getRewardDtos().get(0).getCoin()).isEqualTo(100);
        assertThat(response.getSubscribeDtos().get(0).getUsername()).isEqualTo("user1");
        assertThat(response.getSubscribeDtos().get(0).getCoin()).isEqualTo(3);
    }

    @Test
    public void getAuthorIncomeFailWhenPermissionDeny() {
        GetAuthorIncomeRequest request = new GetAuthorIncomeRequest();
        request.setUserId(userId);
        request.setWorksId(1L);
        request.setWorksType(WorksTypeEnum.NOVEL);
        assertException(PermissionDenyException.class, () -> {
            userService.getAuthorIncome(request);
        });
    }
}
