package com.qiangdong.reader.user_activity_collection;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.TestConstant;
import com.qiangdong.reader.dto.UserActivityCollectionDto;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_activity_collection.AddUserActivityCollectionRequest;
import com.qiangdong.reader.serviceImpl.UserActivityCollectionServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserActivityCollectionAddUserActivityCollectionTest extends BaseTest {
    @Autowired
    private UserActivityCollectionServiceImpl userActivityCollectionService;

    @Test
    public void addUserActivityCollectionSuccessful(){
        AddUserActivityCollectionRequest request = new AddUserActivityCollectionRequest();
        request.setUserId(userId);
        request.setUserActivityId(3L);
        userActivityCollectionService.addUserActivityCollection(request, new UserActivity());
        BaseRequest listRequest = new BaseRequest();
        listRequest.setUserId(TestConstant.userId);
        List<UserActivityCollectionDto> data =
                userActivityCollectionService.listUserActivityCollection(listRequest).getList();
        assertThat(data.size()).isEqualTo(3);
    }

    @Test
    public void addUserActivityCollectionWhenUserActivityNoExist(){
        AddUserActivityCollectionRequest request = new AddUserActivityCollectionRequest();
        request.setUserId(userId);
        request.setUserActivityId(-1L);
        assertException(InvalidArgumentException.class, ()->{
            userActivityCollectionService.addUserActivityCollection(request, new UserActivity());
        });
    }

    @Test
    public void addUserActivityCollectionWhenUserActivityCollectionExist(){
        AddUserActivityCollectionRequest request = new AddUserActivityCollectionRequest();
        request.setUserId(userId);
        request.setUserActivityId(1L);
        assertException(InvalidArgumentException.class, ()->{
            userActivityCollectionService.addUserActivityCollection(request, new UserActivity());
        });
    }

}
