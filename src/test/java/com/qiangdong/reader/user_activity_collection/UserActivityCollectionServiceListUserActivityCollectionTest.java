package com.qiangdong.reader.user_activity_collection;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.TestConstant;
import com.qiangdong.reader.dto.UserActivityCollectionDto;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.serviceImpl.UserActivityCollectionServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserActivityCollectionServiceListUserActivityCollectionTest extends BaseTest {

    @Autowired
    private UserActivityCollectionServiceImpl userActivityCollectionService;

    @Test
    public void listUserActivityCollectionSuccessful(){
        BaseRequest request = new BaseRequest();
        request.setUserId(userId);
        List<UserActivityCollectionDto> listUserActivityCollection =
                userActivityCollectionService.listUserActivityCollection(request).getList();
        assertThat(listUserActivityCollection.size()).isEqualTo(2);
        assertThat(listUserActivityCollection.get(0).getUsername()).isEqualTo("user1");
    }

}
