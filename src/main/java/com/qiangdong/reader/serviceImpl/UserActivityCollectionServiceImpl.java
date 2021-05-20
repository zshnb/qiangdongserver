package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.dto.UserActivityCollectionDto;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.entity.UserActivityCollection;
import com.qiangdong.reader.dao.UserActivityCollectionMapper;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_activity_collection.AddUserActivityCollectionRequest;
import com.qiangdong.reader.request.user_activity_collection.CancelCollectUserActivityRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IUserActivityCollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-23
 */
@Service
public class UserActivityCollectionServiceImpl extends ServiceImpl<UserActivityCollectionMapper, UserActivityCollection>
        implements IUserActivityCollectionService {

    private final UserActivityCollectionMapper userActivityCollectionMapper;
    private final PageUtil pageUtil;

    public UserActivityCollectionServiceImpl(
        UserActivityCollectionMapper userActivityCollectionMapper, PageUtil pageUtil) {
        this.userActivityCollectionMapper = userActivityCollectionMapper;
        this.pageUtil = pageUtil;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<UserActivityCollection> addUserActivityCollection(AddUserActivityCollectionRequest request,
                                                                      UserActivity userActivity) {
        QueryWrapper<UserActivityCollection> queryWrapper = new QueryWrapper<UserActivityCollection>()
            .eq("user_activity_id", request.getUserActivityId())
            .eq("user_id", request.getUserId());
        UserActivityCollection userActivityCollection = getOne(queryWrapper);
        AssertUtil.assertNull(userActivityCollection, "重复收藏");
        userActivityCollection = new UserActivityCollection();
        BeanUtils.copyProperties(request, userActivityCollection);
        save(userActivityCollection);
        return Response.ok(userActivityCollection);
    }

    @Override
    public PageResponse<UserActivityCollectionDto> listUserActivityCollection(BaseRequest request) {
        IPage<UserActivityCollectionDto> listUserActivityCollection =
                userActivityCollectionMapper.listUserActivityCollection(pageUtil.of(request), request.getUserId());
        return PageResponse.of(listUserActivityCollection, request.getPageSize());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Response<String> cancelCollectUserActivity(CancelCollectUserActivityRequest request,
                                                      UserActivity userActivity) {
        remove(new QueryWrapper<UserActivityCollection>()
            .eq("user_id", request.getUserId())
            .eq("user_activity_id", request.getUserActivityId()));
        return Response.ok();
    }
}
