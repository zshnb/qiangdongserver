package com.qiangdong.reader.controller;


import com.qiangdong.reader.entity.UserHobby;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_hobby.AddUserHobbyRequest;
import com.qiangdong.reader.request.user_hobby.DeleteUserHobbyRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IUserHobbyService;
import com.qiangdong.reader.serviceImpl.UserHobbyServiceImpl;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/user-hobby")
public class UserHobbyController {

    private final UserHobbyServiceImpl userHobbyService;

    public UserHobbyController(UserHobbyServiceImpl userHobbyService) {
        this.userHobbyService = userHobbyService;
    }

    @PostMapping("/add")
    public Response<UserHobby> addUserHobby(@RequestBody @Valid AddUserHobbyRequest request) {
        return userHobbyService.addUserHobby(request);
    }

    @PostMapping("/list")
    public PageResponse<UserHobby> listUserHobby(@RequestBody BaseRequest request) {
        return userHobbyService.listUserHobby(request);
    }

    @DeleteMapping("/{userHobbyId}")
    public Response<String> deleteUserHobby(@RequestBody DeleteUserHobbyRequest request) {
        return userHobbyService.deleteUserHobby(request, new UserHobby());
    }
}
