package com.qiangdong.reader.controller;


import com.qiangdong.reader.entity.Type;
import com.qiangdong.reader.entity.UserPreferType;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_prefer_type.AddUserPreferTypeRequest;
import com.qiangdong.reader.request.user_prefer_type.DeleteUserPreferTypeRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.user.ListUserPreferTypeResponse;
import com.qiangdong.reader.serviceImpl.UserPreferTypeServiceImpl;
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
 * @since 2020-07-19
 */
@RestController
@RequestMapping("/user-prefer-type")
public class UserPreferTypeController {

	@Autowired
	private UserPreferTypeServiceImpl userPreferTypeService;

	@PostMapping("/add")
	public Response<UserPreferType> addUserPreferType(@RequestBody AddUserPreferTypeRequest request) {
		return userPreferTypeService.addUserPreferType(request, new Type());
	}

	@PostMapping("/list")
	public ListUserPreferTypeResponse listUserPreferType(@RequestBody BaseRequest request) {
		return userPreferTypeService.listUserPreferType(request);
	}

	@DeleteMapping("/{userPreferTypeId}")
	public Response<String> deleteUserPreferType(@RequestBody DeleteUserPreferTypeRequest request) {
		return userPreferTypeService.deleteUserPreferType(request, new UserPreferType());
	}
}
