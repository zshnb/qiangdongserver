package com.qiangdong.reader.service;

import com.qiangdong.reader.entity.UserHobby;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_hobby.AddUserHobbyRequest;
import com.qiangdong.reader.request.user_hobby.DeleteUserHobbyRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-17
 */
public interface IUserHobbyService extends IService<UserHobby> {
    Response<UserHobby> addUserHobby(AddUserHobbyRequest request);

    PageResponse<UserHobby> listUserHobby(BaseRequest request);

    Response<String> deleteUserHobby(DeleteUserHobbyRequest request, UserHobby userHobby);
}
