package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.entity.UserHobby;
import com.qiangdong.reader.dao.UserHobbyMapper;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_hobby.AddUserHobbyRequest;
import com.qiangdong.reader.request.user_hobby.DeleteUserHobbyRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IUserHobbyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-17
 */
@Service
public class UserHobbyServiceImpl extends ServiceImpl<UserHobbyMapper, UserHobby> implements IUserHobbyService {

    private final PageUtil pageUtil;

    public UserHobbyServiceImpl(PageUtil pageUtil) {
        this.pageUtil = pageUtil;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<UserHobby> addUserHobby(AddUserHobbyRequest request) {
        UserHobby mayExist = getOne(new QueryWrapper<UserHobby>()
            .select("id")
            .eq("user_id", request.getUserId())
            .eq("type", request.getType())
            .eq("name", request.getName()));
        AssertUtil.assertNull(mayExist, "用户爱好已存在");
        UserHobby userHobby = new UserHobby();
        userHobby.setUserId(request.getUserId());
        userHobby.setName(request.getName());
        userHobby.setType(request.getType());
        save(userHobby);
        return Response.ok(userHobby);
    }

    @Override
    public PageResponse<UserHobby> listUserHobby(BaseRequest request) {
        IPage<UserHobby> userHobbyIPage = page(pageUtil.of(request), new QueryWrapper<UserHobby>()
            .eq("user_id", request.getUserId()));
        return PageResponse.of(userHobbyIPage, request.getPageSize());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<String> deleteUserHobby(DeleteUserHobbyRequest request, UserHobby userHobby) {
        removeById(request.getUserHobbyId());
        return Response.ok();
    }
}
