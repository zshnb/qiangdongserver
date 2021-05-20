package com.qiangdong.reader.controller;


import com.qiangdong.reader.dto.UserActivityCollectionDto;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.entity.UserActivityCollection;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_activity_collection.AddUserActivityCollectionRequest;
import com.qiangdong.reader.request.user_activity_collection.CancelCollectUserActivityRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.UserActivityCollectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-23
 */
@RestController
@RequestMapping("/user-activity-collection")
public class UserActivityCollectionController {

    @Autowired
    private UserActivityCollectionServiceImpl userActivityCollectionService;

    @PostMapping("/add")
    public Response<UserActivityCollection> addUserActivityCollection(
            @RequestBody AddUserActivityCollectionRequest request) {
        return userActivityCollectionService.addUserActivityCollection(request, new UserActivity());
    }

    @PostMapping("/list")
    public PageResponse<UserActivityCollectionDto> listUserActivityCollection(@RequestBody BaseRequest request) {
        return userActivityCollectionService.listUserActivityCollection(request);
    }

    @DeleteMapping("/cancel-collect")
    public Response<String> cancelCollectUserActivity(@RequestBody CancelCollectUserActivityRequest request) {
        return userActivityCollectionService.cancelCollectUserActivity(request, new UserActivity());
    }

}
