package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.dao.UserChatMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.dto.BlockUserDto;
import com.qiangdong.reader.entity.BlockUser;
import com.qiangdong.reader.dao.BlockUserMapper;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserChat;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.block_user.AddBlockUserRequest;
import com.qiangdong.reader.request.block_user.DeleteBlockUserRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IBlockUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-27
 */
@Service
public class BlockUserServiceImpl extends ServiceImpl<BlockUserMapper, BlockUser> implements IBlockUserService {
	private final UserMapper userMapper;
	private final PageUtil pageUtil;
	private final UserChatMapper userChatMapper;
	private final BlockUserMapper blockUserMapper;

	public BlockUserServiceImpl(UserMapper userMapper, PageUtil pageUtil,
	                            UserChatMapper userChatMapper, BlockUserMapper blockUserMapper) {
		this.userMapper = userMapper;
		this.pageUtil = pageUtil;
		this.userChatMapper = userChatMapper;
		this.blockUserMapper = blockUserMapper;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<BlockUser> addBlockUser(AddBlockUserRequest request) {
		if (request.getTargetUserId().equals(request.getUserId())) {
			throw new InvalidArgumentException("不能添加自己为黑名单用户");
		}
		User targetUser = userMapper.selectById(request.getTargetUserId());
		AssertUtil.assertNotNull(targetUser, "目标用户不存在");
		BlockUser blockUser = new BlockUser();
		BeanUtils.copyProperties(request, blockUser);
		save(blockUser);
		userChatMapper.delete(new QueryWrapper<UserChat>()
				.eq("user_id", request.getUserId())
				.eq("chat_user_id", request.getTargetUserId()));
		return Response.ok(blockUser);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<String> deleteBlockUser(DeleteBlockUserRequest request, BlockUser blockUser) {
		removeById(request.getBlockUserId());
		return Response.ok();
	}

	@Override
	public PageResponse<BlockUserDto> listBlockUser(BaseRequest request) {
		IPage<BlockUserDto> blockUserIPage = blockUserMapper.findByUserId(pageUtil.of(request), request.getUserId());
		return PageResponse.of(blockUserIPage, request.getPageSize());
	}
}
