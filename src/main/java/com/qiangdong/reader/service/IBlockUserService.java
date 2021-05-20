package com.qiangdong.reader.service;

import com.qiangdong.reader.dto.BlockUserDto;
import com.qiangdong.reader.entity.BlockUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.block_user.AddBlockUserRequest;
import com.qiangdong.reader.request.block_user.DeleteBlockUserRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-27
 */
public interface IBlockUserService extends IService<BlockUser> {

	@Transactional(rollbackFor = RuntimeException.class)
	Response<BlockUser> addBlockUser(AddBlockUserRequest request);

	@Transactional(rollbackFor = RuntimeException.class)
	Response<String> deleteBlockUser(DeleteBlockUserRequest request, BlockUser blockUser);

	PageResponse<BlockUserDto> listBlockUser(BaseRequest request);
}
