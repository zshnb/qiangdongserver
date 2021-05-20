package com.qiangdong.reader.service;

import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.UserPreferType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_prefer_type.AddUserPreferTypeRequest;
import com.qiangdong.reader.request.user_prefer_type.DeleteUserPreferTypeRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.user.ListUserPreferTypeResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-19
 */
public interface IUserPreferTypeService extends IService<UserPreferType> {

	Response<UserPreferType> addUserPreferType(AddUserPreferTypeRequest request, Type type);

	Response<String> deleteUserPreferType(DeleteUserPreferTypeRequest request, UserPreferType userPreferType);

	ListUserPreferTypeResponse listUserPreferType(BaseRequest request);
}
