package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.dao.CommentMapper;
import com.qiangdong.reader.dao.FollowRelationMapper;
import com.qiangdong.reader.dao.NoticeMapper;
import com.qiangdong.reader.dao.UserActivityMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.dto.FollowRelationDto;
import com.qiangdong.reader.dto.MessageDto;
import com.qiangdong.reader.dto.comment.CommentDto;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.entity.Message;
import com.qiangdong.reader.dao.MessageMapper;
import com.qiangdong.reader.entity.Notice;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.enums.message.MessageReadStatusEnum;
import com.qiangdong.reader.enums.message.MessageTypeEnum;
import com.qiangdong.reader.enums.user_activity.AgreeActivityTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.comment.MentionRequest;
import com.qiangdong.reader.request.message.ListMessageByTypeRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.message.GetMessageSummaryResponse;
import com.qiangdong.reader.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * <p>
 * 消息 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-31
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {
	private final PageUtil pageUtil;
	private final CommentMapper commentMapper;
	private final UserActivityMapper userActivityMapper;
	private final FollowRelationMapper followRelationMapper;
	private final NoticeMapper noticeMapper;
	private final UserMapper userMapper;

	public MessageServiceImpl(PageUtil pageUtil, CommentMapper commentMapper,
	                          UserActivityMapper userActivityMapper,
	                          FollowRelationMapper followRelationMapper,
	                          NoticeMapper noticeMapper, UserMapper userMapper) {
		this.pageUtil = pageUtil;
		this.commentMapper = commentMapper;
		this.userActivityMapper = userActivityMapper;
		this.followRelationMapper = followRelationMapper;
		this.noticeMapper = noticeMapper;
		this.userMapper = userMapper;
	}

	/**
	 * 消息提醒，根据类型提醒不同的消息
	* */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public PageResponse<MessageDto> listMessageByType(ListMessageByTypeRequest request) {
		IPage<Message> messageIPage;
		if (request.getMessageType().equals(MessageTypeEnum.SYSTEM_MESSAGE)) {
			messageIPage = page(pageUtil.of(request), new QueryWrapper<Message>()
				.eq("type", request.getMessageType())
				.orderByDesc("create_at"));
		} else {
			messageIPage = page(pageUtil.of(request), new QueryWrapper<Message>()
				.eq("user_id", request.getUserId())
				.eq("type", request.getMessageType())
				.orderByDesc("create_at"));
		}
		if (messageIPage.getTotal() == 0) {
			return PageResponse.of();
		}

		List<Message> messages = messageIPage.getRecords();
		messages.forEach(it -> it.setReadStatus(MessageReadStatusEnum.READ));
		updateBatchById(messages);
		switch (request.getMessageType()) {
			case REPLY: {
				List<Long> commendIds = messages.stream().map(Message::getAssociateId)
					.collect(Collectors.toList());
				List<CommentDto> commentDtos = commentMapper.findByIdIn(commendIds);
				List<MessageDto> messageDtos = commentDtos.stream().map(commentDto -> {
					MessageDto messageDto = new MessageDto();
					messageDto.setAssociateId(commentDto.getId());
					messageDto.setMessage(commentDto);
					messageDto.setCreateAt(commentDto.getCreateAt());
					messageDto.setMessageType(request.getMessageType());
					return messageDto;
				}).collect(Collectors.toList());
				return PageResponse.of(messageDtos, request.getPageSize(), messageIPage.getTotal());
			}
			case COMMENT_MENTION:
			case USER_ACTIVITY_MENTION: {
				List<Long> activityIds = messages.stream()
					.filter(it -> it.getType().equals(MessageTypeEnum.USER_ACTIVITY_MENTION))
					.map(Message::getAssociateId).collect(Collectors.toList());
				List<UserActivityDto> userActivityDtos = new ArrayList<>();
				if (!CollectionUtils.isEmpty(activityIds)) {
					 userActivityDtos = userActivityMapper.findCreateActivityByIdIn(activityIds);
				}
				List<CommentDto> commentDtos = new ArrayList<>();
				List<Long> commentIds = messages.stream()
					.filter(it -> it.getType().equals(MessageTypeEnum.COMMENT_MENTION))
					.map(Message::getAssociateId).collect(Collectors.toList());

				if (!CollectionUtils.isEmpty(commentIds)) {
				    commentDtos = commentMapper.findByIdIn(commentIds);
				}
				List<MessageDto> messageDtos = userActivityDtos.stream().map(activityDto -> {
					MessageDto messageDto = new MessageDto();
					messageDto.setAssociateId(activityDto.getId());
					messageDto.setMessage(activityDto);
					messageDto.setCreateAt(activityDto.getCreateAt());
					messageDto.setMessageType(request.getMessageType());
					messageDto.setImage(activityDto.getActivityData().getCreateActivity().getImages().get(0));
					return messageDto;
				}).collect(Collectors.toList());
				messageDtos.addAll(commentDtos.stream().map(commentDto -> {
					MessageDto messageDto = new MessageDto();
					messageDto.setAssociateId(commentDto.getId());
					messageDto.setMessage(commentDto);
					messageDto.setCreateAt(commentDto.getCreateAt());
					messageDto.setMessageType(request.getMessageType());
					return messageDto;
				}).collect(Collectors.toList()));
				return PageResponse.of(messageDtos, request.getPageSize(), (long) messageDtos.size());
			}
			case COMMENT: {
				List<Long> activityIds = messages.stream()
					.map(Message::getAssociateId)
					.collect(Collectors.toList());
				List<CommentDto> commentDtos = commentMapper.findCreateActivityComment(activityIds);
				List<MessageDto> messageDtos = commentDtos.stream().map(commentDto -> {
					MessageDto messageDto = new MessageDto();
					messageDto.setAssociateId(commentDto.getAssociateId());
					messageDto.setMessage(commentDto);
					messageDto.setCreateAt(commentDto.getCreateAt());
					messageDto.setMessageType(request.getMessageType());

					UserActivity userActivity = userActivityMapper.findById(commentDto.getAssociateId());
					if (!userActivity.getActivityData().getCreateActivity().getImages().isEmpty()) {
						messageDto.setImage(userActivity.getActivityData().getCreateActivity().getImages().get(0));
					}
					return messageDto;
				}).collect(Collectors.toList());
				return PageResponse.of(messageDtos, request.getPageSize(), messageIPage.getTotal());
			}
			case AGREE: {
				List<Long> activityIds = messages.stream().map(Message::getAssociateId)
					.collect(Collectors.toList());
				List<UserActivityDto> activityDtos = userActivityMapper.findAgreeActivityByIdIn(activityIds);
				List<MessageDto> messageDtos = activityDtos.stream().map(activityDto -> {
					MessageDto messageDto = new MessageDto();
					if (activityDto.getActivityData().getAgreeActivity().getType().equals(AgreeActivityTypeEnum.USER_ACTIVITY)) {
						messageDto.setAssociateId(activityDto.getActivityData().getAgreeActivity().getActivityId());
						UserActivity userActivity = userActivityMapper.findById(activityDto.getActivityData().getAgreeActivity().getActivityId());
						if (!userActivity.getActivityData().getCreateActivity().getImages().isEmpty()) {
							messageDto.setImage(userActivity.getActivityData().getCreateActivity().getImages().get(0));
						}
					} else {
						messageDto.setAssociateId(activityDto.getActivityData().getAgreeActivity().getCommentId());
					}
					messageDto.setCreateAt(activityDto.getCreateAt());
					messageDto.setMessage(activityDto);
					messageDto.setMessageType(request.getMessageType());

					return messageDto;
				}).collect(Collectors.toList());
				return PageResponse.of(messageDtos, request.getPageSize(), messageIPage.getTotal());
			}
			case FOLLOW: {
				List<Long> followRelationIds = messages.stream().map(Message::getAssociateId)
					.collect(Collectors.toList());
				List<FollowRelationDto> followRelationDtos = followRelationMapper.findFollowerByIdIn(followRelationIds);
				List<MessageDto> messageDtos = followRelationDtos.stream().map(followRelationDto -> {
					MessageDto messageDto = new MessageDto();
					messageDto.setAssociateId(followRelationDto.getFollowerId());
					messageDto.setCreateAt(followRelationDto.getCreateAt());
					messageDto.setMessage(followRelationDto);
					messageDto.setMessageType(request.getMessageType());
					return messageDto;
				}).collect(Collectors.toList());
				return PageResponse.of(messageDtos, request.getPageSize(), messageIPage.getTotal());
			}
			case SYSTEM_MESSAGE: {
				List<Long> noticeIds = messages.stream().map(Message::getAssociateId)
					.collect(Collectors.toList());
				List<Notice> notices = noticeMapper.findByIdIn(noticeIds);
				List<MessageDto> messageDtos = notices.stream().map(notice -> {
					MessageDto messageDto = new MessageDto();
						messageDto.setAssociateId(notice.getId());
					messageDto.setCreateAt(notice.getCreateAt());
					messageDto.setMessage(notice);
					messageDto.setMessageType(request.getMessageType());
					return messageDto;
				}).collect(Collectors.toList());
				return PageResponse.of(messageDtos, request.getPageSize(), messageIPage.getTotal());
			}
			default: throw new InvalidArgumentException("无效的消息类型");
		}
	}

	/**
	 * 消息通知页面，不同类型的新消息数据
	 * */
	@Override
	public GetMessageSummaryResponse getMessageSummary(BaseRequest request) {
		int systemMessageCount = count(new QueryWrapper<Message>()
			.eq("read_status", MessageReadStatusEnum.UNREAD)
			.eq("user_id", request.getUserId())
			.eq("type", MessageTypeEnum.SYSTEM_MESSAGE));
		int agreeActivityMessageCount = count(new QueryWrapper<Message>()
			.eq("read_status", MessageReadStatusEnum.UNREAD)
			.eq("user_id", request.getUserId())
			.eq("type", MessageTypeEnum.AGREE));
		int followMessageCount = count(new QueryWrapper<Message>()
			.eq("read_status", MessageReadStatusEnum.UNREAD)
			.eq("user_id", request.getUserId())
			.eq("type", MessageTypeEnum.FOLLOW));
		int mentionMessageCount = count(new QueryWrapper<Message>()
			.eq("read_status", MessageReadStatusEnum.UNREAD)
			.eq("user_id", request.getUserId())
			.and(wrapper -> wrapper.eq("type", MessageTypeEnum.USER_ACTIVITY_MENTION).or()
				.eq("type", MessageTypeEnum.COMMENT_MENTION)));

		GetMessageSummaryResponse response = new GetMessageSummaryResponse();
		response.setSystemMessageCount(systemMessageCount);
		response.setAgreeActivityMessageCount(agreeActivityMessageCount);
		response.setFollowMessageCount(followMessageCount);
		response.setMentionMessageCount(mentionMessageCount);
		return response;
	}

	/**
	 * 提到某人
	 * */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<String> mention(MentionRequest request) {
		switch (request.getType()) {
			case USER_ACTIVITY_MENTION: {
				UserActivity userActivity = userActivityMapper.findById(request.getAssociateId());
				AssertUtil.assertNotNull(userActivity, "动态不存在");
				break;
			}
			case COMMENT_MENTION: {
				Comment comment = commentMapper.selectById(request.getAssociateId());
				AssertUtil.assertNotNull(comment, "评论不存在");
				break;
			}
			default: throw new InvalidArgumentException("无效的提到类型");
		}
		User user = userMapper.selectById(request.getTargetUserId());
		AssertUtil.assertNotNull(user, "用户不存在");
		Message message = new Message();
		message.setReadStatus(MessageReadStatusEnum.UNREAD);
		message.setUserId(request.getTargetUserId());
		message.setAssociateId(request.getAssociateId());
		message.setType(request.getType());
		save(message);
		return Response.ok();
	}
}
