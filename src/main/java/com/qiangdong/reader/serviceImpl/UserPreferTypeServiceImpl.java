package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.dao.TypeMapper;
import com.qiangdong.reader.dto.UserPreferTypeDto;
import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.UserPreferType;
import com.qiangdong.reader.dao.UserPreferTypeMapper;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_prefer_type.AddUserPreferTypeRequest;
import com.qiangdong.reader.request.user_prefer_type.DeleteUserPreferTypeRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.user.ListUserPreferTypeResponse;
import com.qiangdong.reader.service.IUserPreferTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AssertUtil;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-19
 */
@Service
public class UserPreferTypeServiceImpl extends ServiceImpl<UserPreferTypeMapper, UserPreferType> implements
    IUserPreferTypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private UserPreferTypeMapper userPreferTypeMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<UserPreferType> addUserPreferType(AddUserPreferTypeRequest request, Type type) {
        UserPreferType userPreferType = getOne(new QueryWrapper<UserPreferType>()
            .eq("user_id", request.getUserId())
            .eq("type_id", request.getTypeId()));
        AssertUtil.assertNull(userPreferType, "阅读喜好已存在");
        userPreferType = new UserPreferType();
        BeanUtils.copyProperties(request, userPreferType);
        save(userPreferType);
        return Response.ok(userPreferType);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Response<String> deleteUserPreferType(DeleteUserPreferTypeRequest request, UserPreferType userPreferType) {
        removeById(request.getUserPreferTypeId());
        return Response.ok();
    }

    @Override
    public ListUserPreferTypeResponse listUserPreferType(BaseRequest request) {
        List<UserPreferTypeDto> userPreferType = userPreferTypeMapper.findUserPreferTypeByUserId(request.getUserId());
        List<Type> allType = typeMapper.selectList(new QueryWrapper<Type>().
            select("id, name, parent_id, belong"));
        ListUserPreferTypeResponse response = new ListUserPreferTypeResponse();
        response.setUserPreferType(userPreferType);
        response.setAllType(allType);
        return response;
    }

}
