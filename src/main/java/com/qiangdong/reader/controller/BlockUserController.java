package com.qiangdong.reader.controller;


import com.qiangdong.reader.dto.BlockUserDto;
import com.qiangdong.reader.entity.BlockUser;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.block_user.AddBlockUserRequest;
import com.qiangdong.reader.request.block_user.DeleteBlockUserRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.BlockUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-27
 */
@RestController
@RequestMapping("/block-user")
public class BlockUserController {
	@Autowired
	private BlockUserServiceImpl blockUserService;

	@PostMapping("/add")
	public Response<BlockUser> addBlockUser(@RequestBody AddBlockUserRequest request) {
		return blockUserService.addBlockUser(request);
	}

	@DeleteMapping("/{blockUserId}")
	public Response<String> deleteBlockUser(@RequestBody DeleteBlockUserRequest request) {
		return blockUserService.deleteBlockUser(request, new BlockUser());
	}

	@PostMapping("/list")
	public PageResponse<BlockUserDto> listBlockUser(@RequestBody BaseRequest request) {
		return blockUserService.listBlockUser(request);
	}
}
