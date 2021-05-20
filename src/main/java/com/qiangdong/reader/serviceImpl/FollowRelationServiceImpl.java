package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.dao.FollowRelationMapper;
import com.qiangdong.reader.dao.MessageMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.dto.FollowRelationDto;
import com.qiangdong.reader.entity.FollowRelation;
import com.qiangdong.reader.entity.Message;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.enums.message.MessageReadStatusEnum;
import com.qiangdong.reader.enums.message.MessageTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.follow_relation.FollowUserRequest;
import com.qiangdong.reader.request.follow_relation.ListFansUserRequest;
import com.qiangdong.reader.request.follow_relation.ListFollowUserRequest;
import com.qiangdong.reader.request.follow_relation.UnFollowUserRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.service.IFollowRelationService;
import com.qiangdong.reader.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 关注表 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-25
 */
@Service
public class FollowRelationServiceImpl extends ServiceImpl<FollowRelationMapper, FollowRelation>
	implements IFollowRelationService {

	private final FollowRelationMapper followRelationMapper;
	private final UserMapper userMapper;
	private final MessageMapper messageMapper;
	private final PageUtil pageUtil;

	public FollowRelationServiceImpl(FollowRelationMapper followRelationMapper,
	                                 UserMapper userMapper, MessageMapper messageMapper,
	                                 PageUtil pageUtil) {
		this.followRelationMapper = followRelationMapper;
		this.userMapper = userMapper;
		this.messageMapper = messageMapper;
		this.pageUtil = pageUtil;
	}

	/**
	* 关注
	* */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<FollowRelation> followUser(FollowUserRequest request) {
		User user = userMapper.selectById(request.getTargetUserId());
		if (user == null) {
			throw new InvalidArgumentException("关注的用户不存在");
		}

		QueryWrapper<FollowRelation> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("follower_id", request.getUserId())
			.eq("followed_id", request.getTargetUserId());
		FollowRelation existFollowRelation = getOne(queryWrapper);
		if (existFollowRelation != null) {
			throw new InvalidArgumentException("已关注");
		}

		FollowRelation relation = new FollowRelation();
		relation.setFollowedId(request.getTargetUserId());
		relation.setFollowerId(request.getUserId());
		relation.setFollowEach(false);

		// 查找有无互相关注的记录
		queryWrapper.clear();
		queryWrapper.eq("follower_id", request.getTargetUserId())
			.eq("followed_id", request.getUserId());
		FollowRelation reverseFollowRelation = getOne(queryWrapper);
		if (reverseFollowRelation != null) {
			relation.setFollowEach(true);
			reverseFollowRelation.setFollowEach(true);
			updateById(reverseFollowRelation);
		}
		save(relation);

		Message message = new Message();
		message.setUserId(request.getTargetUserId());
		message.setReadStatus(MessageReadStatusEnum.UNREAD);
		message.setAssociateId(relation.getId());
		message.setType(MessageTypeEnum.FOLLOW);
		messageMapper.insert(message);
		return Response.ok(relation);
	}

	/**
	 * 取关
	 * */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<String> unFollowUser(UnFollowUserRequest request) {
		User user = userMapper.selectById(request.getTargetUserId());
		if (user == null) {
			throw new InvalidArgumentException("取消关注的用户不存在");
		}
		QueryWrapper<FollowRelation> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("follower_id", request.getUserId())
			.eq("followed_id", request.getTargetUserId());
		FollowRelation existFollowRelation = getOne(queryWrapper);
		if (existFollowRelation == null) {
			throw new InvalidArgumentException("未关注");
		}
		remove(queryWrapper);

		// 查找有无互相关注的记录
		queryWrapper.clear();
		queryWrapper.eq("follower_id", request.getTargetUserId())
			.eq("followed_id", request.getUserId());
		FollowRelation reverseFollowRelation = getOne(queryWrapper);
		if (reverseFollowRelation != null) {
			reverseFollowRelation.setFollowEach(false);
			updateById(reverseFollowRelation);
		}

		// 删除之前发送的关注消息
		messageMapper.delete(new QueryWrapper<Message>()
			.eq("type", MessageTypeEnum.FOLLOW)
			.eq("associate_id", existFollowRelation.getId()));
		return Response.ok();
	}

	/**
	 * 获取关注用户列表
	 * */
	@Override
	public PageResponse<FollowRelationDto> listFollowUser(ListFollowUserRequest request) {
		IPage<FollowRelationDto> followRelationDtos = followRelationMapper.findFollowUser(
			pageUtil.of(request), request.getTargetUserId());
		return PageResponse.of(followRelationDtos, request.getPageSize());
	}

	/**
	 * 获取粉丝列表
	 * */
	@Override
	public PageResponse<FollowRelationDto> listFansUser(ListFansUserRequest request) {
		IPage<FollowRelationDto> followRelationDtos = followRelationMapper.findFansUser(
			pageUtil.of(request), request.getTargetUserId());
		return PageResponse.of(followRelationDtos, request.getPageSize());
	}
}
