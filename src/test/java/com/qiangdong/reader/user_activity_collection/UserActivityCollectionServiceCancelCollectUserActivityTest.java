package com.qiangdong.reader.user_activity_collection;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.UserActivityCollectionDto;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_activity_collection.CancelCollectUserActivityRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.UserActivityCollectionServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserActivityCollectionServiceCancelCollectUserActivityTest extends BaseTest {

    @Autowired
    private UserActivityCollectionServiceImpl userActivityCollectionService;

    @Test
    public void cancelCollectUserActivitySuccessful() {
        CancelCollectUserActivityRequest request = new CancelCollectUserActivityRequest();
        request.setUserId(userId);
        request.setUserActivityId(1L);
        userActivityCollectionService.cancelCollectUserActivity(request, new UserActivity());

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setUserId(userId);
        PageResponse<UserActivityCollectionDto> response =
            userActivityCollectionService.listUserActivityCollection(baseRequest);
        assertThat(response.getList().size()).isEqualTo(1);
    }

    @Test
    public void cancelCollectUserActivityFailedWhenActivityNotExist() {
        CancelCollectUserActivityRequest request = new CancelCollectUserActivityRequest();
        request.setUserId(userId);
        request.setUserActivityId(-1L);
        assertException(InvalidArgumentException.class, () -> {
            userActivityCollectionService.cancelCollectUserActivity(request, new UserActivity());
        });
    }
}
