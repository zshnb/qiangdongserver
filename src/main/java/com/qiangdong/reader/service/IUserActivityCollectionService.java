package com.qiangdong.reader.service;

import com.qiangdong.reader.dto.UserActivityCollectionDto;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.entity.UserActivityCollection;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_activity_collection.AddUserActivityCollectionRequest;
import com.qiangdong.reader.request.user_activity_collection.CancelCollectUserActivityRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-23
 */
public interface IUserActivityCollectionService extends IService<UserActivityCollection> {
    Response<UserActivityCollection> addUserActivityCollection(AddUserActivityCollectionRequest request,
                                                               UserActivity userActivity);

    PageResponse<UserActivityCollectionDto> listUserActivityCollection(BaseRequest request);

    @Transactional(rollbackFor = RuntimeException.class)
    Response<String> cancelCollectUserActivity(CancelCollectUserActivityRequest request,
                                               UserActivity userActivity);
}
