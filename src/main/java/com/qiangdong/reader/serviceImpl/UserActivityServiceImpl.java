package com.qiangdong.reader.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiangdong.reader.annotation.RequireAdmin;
import com.qiangdong.reader.annotation.Validation;
import com.qiangdong.reader.common.TopicConstant;
import com.qiangdong.reader.common.UserActivityConstant;
import com.qiangdong.reader.common.UserConstant;
import com.qiangdong.reader.dao.*;
import com.qiangdong.reader.dto.user_activity.*;
import com.qiangdong.reader.entity.*;
import com.qiangdong.reader.enums.comment.CommentTypeEnum;
import com.qiangdong.reader.enums.message.MessageReadStatusEnum;
import com.qiangdong.reader.enums.message.MessageTypeEnum;
import com.qiangdong.reader.enums.user_activity.ActivityTypeEnum;
import com.qiangdong.reader.enums.user_activity.AgreeActivityTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.user_activity.*;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.search.UserActivityForSearch;
import com.qiangdong.reader.service.IUserActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.NumberUtil;
import com.qiangdong.reader.utils.PageUtil;
import com.qiangdong.reader.utils.UserActivityUtil;
import com.qiangdong.reader.validate.user_activity.AddOrUpdateActivityValidator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * <p>
 * 用户动态表 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-25
 */
@Service
public class UserActivityServiceImpl extends ServiceImpl<UserActivityMapper, UserActivity> implements IUserActivityService {
	private final UserActivityMapper userActivityMapper;
	private final UserActivityTopicMapper userActivityTopicMapper;
	private final FollowRelationMapper followRelationMapper;
	private final TopicMapper topicMapper;
	private final MessageMapper messageMapper;
	private final PageUtil pageUtil;
	private final RedisTemplate<String, String> redisTemplate;
	private final RedisTemplate<String, Topic> hotTopicRedisTemplate;
	private final RedisTemplate<String, CreateActivity> createActivityRedisTemplate;
	private final RedisTemplate<String, List<Long>> userWithAgreeActivityRedisTemplate;
	private final NumberUtil numberUtil;
	private final CommentMapper commentMapper;
	private final ActivityReadHistoryMapper activityReadHistoryMapper;
	private final UserActivityUtil userActivityUtil;
	private final ElasticsearchTemplate elasticsearchTemplate;

	public UserActivityServiceImpl(UserActivityMapper userActivityMapper,
	                               UserActivityTopicMapper userActivityTopicMapper,
	                               FollowRelationMapper followRelationMapper,
	                               TopicMapper topicMapper,
	                               MessageMapper messageMapper, PageUtil pageUtil,
	                               RedisTemplate<String, String> redisTemplate,
	                               RedisTemplate<String, Topic> hotTopicRedisTemplate,
	                               RedisTemplate<String, CreateActivity> createActivityRedisTemplate,
	                               RedisTemplate<String, List<Long>> userWithAgreeActivityRedisTemplate,
	                               NumberUtil numberUtil,
	                               CommentMapper commentMapper,
	                               ActivityReadHistoryMapper activityReadHistoryMapper,
	                               UserActivityUtil userActivityUtil,
	                               ElasticsearchTemplate elasticsearchTemplate) {
		this.userActivityMapper = userActivityMapper;
		this.userActivityTopicMapper = userActivityTopicMapper;
		this.followRelationMapper = followRelationMapper;
		this.topicMapper = topicMapper;
		this.messageMapper = messageMapper;
		this.pageUtil = pageUtil;
		this.redisTemplate = redisTemplate;
		this.hotTopicRedisTemplate = hotTopicRedisTemplate;
		this.createActivityRedisTemplate = createActivityRedisTemplate;
		this.userWithAgreeActivityRedisTemplate = userWithAgreeActivityRedisTemplate;
		this.numberUtil = numberUtil;
		this.commentMapper = commentMapper;
		this.activityReadHistoryMapper = activityReadHistoryMapper;
		this.userActivityUtil = userActivityUtil;
		this.elasticsearchTemplate = elasticsearchTemplate;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	@Validation(AddOrUpdateActivityValidator.class)
	public Response<UserActivity> addOrUpdateActivity(AddOrUpdateActivityRequest request, User user) {
		if (!request.getTopicIds().isEmpty()) {
			int topicSize = topicMapper.selectList(new QueryWrapper<Topic>()
				.in("id", request.getTopicIds())).size();
			if (topicSize != request.getTopicIds().size()) {
				throw new InvalidArgumentException("话题不存在");
			}
		}

		UserActivity activity;
		ZSetOperations<String, Topic> zSetOperations = hotTopicRedisTemplate.opsForZSet();
		if (request.getActivityId() == 0L) {
			activity = new UserActivity();
			activity.setCreateAt(LocalDateTime.now());
			activity.setUpdateAt(LocalDateTime.now());
			BeanUtils.copyProperties(request, activity);
			userActivityMapper.save(activity);

			if (!request.getTopicIds().isEmpty()) {
				List<UserActivityTopic> topics = request.getTopicIds().stream()
					.map(it -> {
						UserActivityTopic userActivityTopic = new UserActivityTopic();
						userActivityTopic.setTopicId(it);
						userActivityTopic.setUserActivityId(activity.getId());
						return userActivityTopic;
					}).collect(Collectors.toList());
				userActivityTopicMapper.saveAll(topics);
				topicMapper.increaseReferenceCount(request.getTopicIds());

				List<Topic> topicList = topicMapper.selectList(new QueryWrapper<Topic>()
					.in("id", request.getTopicIds()));
				topicList.forEach(it -> zSetOperations.incrementScore(TopicConstant.KEY_HOT_TOPIC, it, 1.0));
			}
		} else {
			activity = userActivityMapper.findById(request.getActivityId());
			if (activity == null) {
				throw new InvalidArgumentException("动态不存在");
			}
			ActivityData activityData = activity.getActivityData();
			if (!activityData.getCreateActivity().getContent().equals(
				request.getActivityData().getCreateActivity().getContent())) {
				activityData.getCreateActivity()
					.setContent(request.getActivityData().getCreateActivity().getContent());
				activity.setActivityData(activityData);
			}
			activity.setUpdateAt(LocalDateTime.now());
			userActivityMapper.update(activity);

			List<Long> existTopics = userActivityTopicMapper.selectList(new QueryWrapper<UserActivityTopic>()
				.eq("user_activity_id", request.getActivityId()))
				.stream()
				.map(UserActivityTopic::getTopicId)
				.collect(Collectors.toList());
			// 删除话题
			if (existTopics.size() >= request.getTopicIds().size()) {
				if (!existTopics.isEmpty()) {
					List<Long> removeTopics = existTopics.stream()
						.filter(it -> !request.getTopicIds().contains(it))
						.collect(Collectors.toList());
					userActivityTopicMapper.delete(new QueryWrapper<UserActivityTopic>()
						.in("topic_id", removeTopics));
					topicMapper.decreaseReferenceCount(removeTopics);

					List<Topic> topicList = topicMapper.selectList(new QueryWrapper<Topic>()
						.in("id", removeTopics));
					topicList.forEach(it -> zSetOperations.incrementScore(TopicConstant.KEY_HOT_TOPIC, it, -1.0));
				}
			} else { // 添加话题
				List<Long> newTopics = request.getTopicIds().stream()
					.filter(it -> !existTopics.contains(it))
					.collect(Collectors.toList());
				if (!newTopics.isEmpty()) {
					List<UserActivityTopic> topics = newTopics.stream()
						.map(it -> {
							UserActivityTopic userActivityTopic = new UserActivityTopic();
							userActivityTopic.setTopicId(it);
							userActivityTopic.setUserActivityId(activity.getId());
							return userActivityTopic;
						}).collect(Collectors.toList());
					userActivityTopicMapper.saveAll(topics);
					topicMapper.increaseReferenceCount(newTopics);

					List<Topic> topicList = topicMapper.selectList(new QueryWrapper<Topic>()
						.in("id", newTopics));
					topicList.forEach(it -> zSetOperations.incrementScore(TopicConstant.KEY_HOT_TOPIC, it, 1.0));
				}
			}
		}
		userActivityUtil.indexUserActivity(activity, user);
		return Response.ok(activity);
	}

	/**
	 * 查看自己的动态列表
	 * */
	@Override
	public PageResponse<UserActivityDto> listActivity(BaseRequest request) {
		IPage<UserActivityDto> activityDtoIPage =
			userActivityMapper.findCreateActivityByUserId(pageUtil.of(request), request.getUserId());
		List<UserActivityDto> activityDtos = activityDtoIPage.getRecords();
		activityDtos.forEach(it -> {
			userActivityUtil.setUserActivityStatus(it, request, false);
		});
		return PageResponse.of(activityDtos, request.getPageSize());
	}

	/**
	* 查看指定用户动态列表
	* */
	@Override
	public PageResponse<UserActivityDto> listUserActivity(ListUserActivityRequest request) {
		IPage<UserActivityDto> activityDtoIPage =
			userActivityMapper.findCreateActivityByUserId(pageUtil.of(request), request.getTargetUserId());
		List<UserActivityDto> activityDtos = activityDtoIPage.getRecords();
		activityDtos.forEach(it -> {
			userActivityUtil.setUserActivityStatus(it, request, false);
		});
		return PageResponse.of(activityDtos, request.getPageSize());
	}

	/**
	 * 推荐动态列表
	 * */
	@Override
	public PageResponse<UserActivityDto> listRecommendActivity(BaseRequest request) {
		IPage<UserActivityDto> activityDtoIPage =
			userActivityMapper.findCreateActivityByHeat(pageUtil.of(request));
		List<UserActivityDto> activityDtos = activityDtoIPage.getRecords();
		activityDtos.forEach(it -> {
			userActivityUtil.setUserActivityStatus(it, request, true);
		});
		return PageResponse.of(activityDtos, request.getPageSize(), activityDtoIPage.getTotal());
	}

	/**
	 * 查看关注者动态列表
	 * */
	@Override
	public PageResponse<UserActivityDto> listFollowUserActivity(BaseRequest request) {
		List<FollowRelation> followRelations = followRelationMapper.selectList(new QueryWrapper<FollowRelation>()
			.eq("follower_id", request.getUserId()));
		List<Long> userIds = followRelations.stream()
			.map(FollowRelation::getFollowedId)
			.collect(Collectors.toList());
		if (!userIds.isEmpty()) {
			IPage<UserActivityDto> activityDtos =
				userActivityMapper.findCreateActivityByUserIdIn(pageUtil.of(request), userIds);
			activityDtos.getRecords().forEach(it -> {
				userActivityUtil.setUserActivityStatus(it, request, true);
			});
			return PageResponse.of(activityDtos, request.getPageSize());
		}
		return PageResponse.of();
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<String> deleteActivity(DeleteActivityRequest request) {
		UserActivity userActivity = getById(request.getActivityId());
		if (userActivity == null) {
			throw new InvalidArgumentException("活动不存在");
		}
		removeById(request.getActivityId());
		return Response.ok();
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<Integer> agreeActivity(AgreeActivityRequest request) {
		UserActivity userActivity = userActivityMapper.findById(request.getActivityId());
		AssertUtil.assertNotNull(userActivity, "动态不存在");

		HashOperations<String, Long, List<Long>> userWithAgreeActivityHashOperation =
			userWithAgreeActivityRedisTemplate.opsForHash();
		String key = String.format(UserConstant.KEY_USER_AGREE_ACTIVITY, request.getUserId() % 10);
		List<Long> userActivityIds = userWithAgreeActivityHashOperation.get(key, request.getActivityId());
		if (CollectionUtils.isEmpty(userActivityIds)) {
			userActivityIds = new ArrayList<>();
		} else if (userActivityIds.contains(request.getActivityId())) {
			throw new InvalidArgumentException("已赞同");
		}
		userActivityIds.add(request.getActivityId());
		userWithAgreeActivityHashOperation.put(key, request.getUserId(), userActivityIds);

		HashOperations<String, Long, CreateActivity> hashOperations = createActivityRedisTemplate.opsForHash();
		key = String.format(UserActivityConstant.KEY_USER_ACTIVITY_AGREE_AGAINST, request.getActivityId() % 10);
		CreateActivity createActivity = hashOperations.get(key, request.getActivityId());
		if (createActivity == null) {
			createActivity = new CreateActivity();
			createActivity.setAgreeCount(1);
		} else {
			createActivity.setAgreeCount(createActivity.getAgreeCount() + 1);
		}
		hashOperations.put(key, request.getActivityId(), createActivity);

		UserActivity agreeUserActivity = new UserActivity();
		agreeUserActivity.setCreateAt(LocalDateTime.now());
		agreeUserActivity.setUpdateAt(LocalDateTime.now());
		agreeUserActivity.setUserId(request.getUserId());
		agreeUserActivity.setType(ActivityTypeEnum.AGREE_ACTIVITY);
		agreeUserActivity.setTop(false);

		AgreeActivity agreeActivity = new AgreeActivity();
		agreeActivity.setType(AgreeActivityTypeEnum.USER_ACTIVITY);
		agreeActivity.setActivityId(request.getActivityId());
		ActivityData activityData = new ActivityData();
		activityData.setAgreeActivity(agreeActivity);
		agreeUserActivity.setActivityData(activityData);
		userActivityMapper.save(agreeUserActivity);

		Message message = new Message();
		message.setUserId(userActivity.getUserId());
		message.setType(MessageTypeEnum.AGREE);
		message.setReadStatus(MessageReadStatusEnum.UNREAD);
		message.setAssociateId(agreeUserActivity.getId());
		messageMapper.insert(message);
		return Response.ok(createActivity.getAgreeCount());
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<Integer> againstActivity(AgainstActivityRequest request) {
		UserActivity userActivity = userActivityMapper.findById(request.getActivityId());
		if (userActivity == null) {
			throw new InvalidArgumentException("动态不存在");
		}
		String key = String.format(UserActivityConstant.KEY_ACTIVITY_AGAINST, request.getActivityId());
		ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
		if (zSetOperations.rank(key, request.getUserId().toString()) != null) {
			throw new InvalidArgumentException("已反对");
		}

		zSetOperations.add(key, request.getUserId().toString(), System.currentTimeMillis());
		UserActivity againstActivity = new UserActivity();
		againstActivity.setUserId(request.getUserId());
		againstActivity.setType(ActivityTypeEnum.AGAINST_ACTIVITY);
		againstActivity.setTop(false);

		AgainstActivity againstActivityData = new AgainstActivity();
		againstActivityData.setActivityId(request.getActivityId());
		ActivityData activityData = new ActivityData();
		activityData.setAgainstActivity(againstActivityData);
		againstActivity.setActivityData(activityData);
		userActivityMapper.save(againstActivity);
		return Response.ok((int)numberUtil.secureLong(zSetOperations.zCard(key)));
	}

	@Override
	public Response<Integer> cancelAgreeActivity(GetUserActivityRequest request, UserActivity userActivity) {
		HashOperations<String, Long, List<Long>> userWithAgreeActivityHashOperation =
			userWithAgreeActivityRedisTemplate.opsForHash();
		String key = String.format(UserConstant.KEY_USER_AGREE_ACTIVITY, request.getUserId() % 10);
		List<Long> userActivityIds = userWithAgreeActivityHashOperation.get(key, request.getUserActivityId());
		if (!CollectionUtils.isEmpty(userActivityIds)) {
			if (!userActivityIds.contains(request.getUserActivityId())) {
				throw new InvalidArgumentException("未赞同");
			} else {
				userActivityIds.remove(request.getUserActivityId());
				userWithAgreeActivityHashOperation.put(key, request.getUserId(), userActivityIds);

				HashOperations<String, Long, CreateActivity> hashOperations = createActivityRedisTemplate.opsForHash();
				key = String.format(UserActivityConstant.KEY_USER_ACTIVITY_AGREE_AGAINST, request.getUserActivityId() % 10);
				CreateActivity createActivity = hashOperations.get(key, request.getUserActivityId());
				AssertUtil.assertNotNull(createActivity, "取消点赞失败");
				createActivity.setAgreeCount(createActivity.getAgreeCount() - 1);
				hashOperations.put(key, request.getUserActivityId(), createActivity);
				return Response.ok(createActivity.getAgreeCount());
			}
		} else {
			throw new InvalidArgumentException("未赞同");
		}
	}

	@Override
	public Response<Integer> cancelAgainstActivity(GetUserActivityRequest request, UserActivity userActivity) {
		String key = String.format(UserActivityConstant.KEY_ACTIVITY_AGAINST, request.getUserActivityId());
		ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
		if (zSetOperations.rank(key, request.getUserId().toString()) == null) {
			throw new InvalidArgumentException("未反对");
		}

		zSetOperations.remove(key, request.getUserId().toString());
		return Response.ok((int)numberUtil.secureLong(zSetOperations.zCard(key)));
	}

	@Override
	public PageResponse<UserActivityDto> listActivityByManager(ListUserActivityRequest request) {
		IPage<UserActivityDto> data =
				userActivityMapper.findActivityByManage(pageUtil.of(request), request.getSearchParam());
		return PageResponse.of(data, request.getPageSize());
	}

	/**
	 * 查看动态详情
	 * */
	@Override
	public Response<UserActivityDto> getUserActivity(GetUserActivityRequest request) {
		UserActivityDto activityDto = userActivityMapper.findCreateActivityById(request.getUserActivityId());
		AssertUtil.assertNotNull(activityDto, "动态不存在");
		userActivityUtil.setUserActivityStatus(activityDto, request, true);

		ActivityReadHistory history = activityReadHistoryMapper.selectOne(new QueryWrapper<ActivityReadHistory>()
		    .select("id")
		    .eq("user_id", request.getUserId())
		    .eq("activity_id", request.getUserActivityId()));
		if (history == null) {
		    history = new ActivityReadHistory();
			history.setActivityId(request.getUserActivityId());
			history.setUserId(request.getUserId());
			activityReadHistoryMapper.insert(history);
		} else {
			history.setUpdateAt(LocalDateTime.now());
			activityReadHistoryMapper.updateById(history);
		}
		return Response.ok(activityDto);
	}

	@Override
	public PageResponse<UserActivityDto> searchUserActivity(SearchUserActivityRequest request) {
		QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(request.getKeyword(),
			"username", "createActivity.content");

		Pageable pageable = PageRequest.of((int)(request.getPageNumber() - 1), request.getPageSize().intValue());
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
			.withQuery(queryBuilder)
			.withPageable(pageable)
			.build();

		Page<UserActivityForSearch> userActivityForSearches =
			elasticsearchTemplate.queryForPage(searchQuery, UserActivityForSearch.class);
		List<UserActivityDto> userActivityDtos = userActivityForSearches.getContent().stream().map(it -> {
			UserActivityDto dto = new UserActivityDto();
			ActivityData activityData = new ActivityData();
			activityData.setCreateActivity(it.getCreateActivity());
			dto.setActivityData(activityData);
			BeanUtils.copyProperties(it, dto);
			userActivityUtil.setUserActivityStatus(dto, request, true);
			return dto;
		}).collect(Collectors.toList());
		return PageResponse.of(userActivityDtos, request.getPageSize(), userActivityForSearches.getTotalElements());
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<UserActivity> changeTopStatus(ChangeActivityTopStatusRequest request, UserActivity userActivity) {
		BeanUtils.copyProperties(request, userActivity);
		updateById(userActivity);
		return Response.ok(userActivity);
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	@RequireAdmin
	public Response<String> deleteActivityByManager(DeleteUserActivityRequest request) {
		if (!request.getActivityIds().isEmpty()) {
			QueryWrapper<UserActivity> queryWrapper = new QueryWrapper<>();
			queryWrapper.select("id")
				.in("id", request.getActivityIds());
			List<UserActivity> userActivities = userActivityMapper.selectList(queryWrapper);
			if (userActivities.size() != request.getActivityIds().size()) {
				throw new InvalidArgumentException("动态不存在");
			}

			userActivityMapper.deleteBatchIds(request.getActivityIds());
		}
		return Response.ok();
	}

	/**
	 * 站内转发动态
	 * */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<UserActivity> shareUserActivity(ShareUserActivityRequest request) {
		UserActivity userActivity = userActivityMapper.findById(request.getShareActivity().getReferActivityId());
		AssertUtil.assertNotNull(userActivity, "动态不存在");
		ShareActivity shareActivity = new ShareActivity();

		int shareCount;
		if (userActivity.getType().equals(ActivityTypeEnum.PUBLISH_ACTIVITY)) {
			// 首次转发取被转发动态的值
			shareActivity.setReferActivityId(userActivity.getId());
			shareCount = userActivity.getActivityData().getCreateActivity().getShareCount();
			userActivity.getActivityData().getCreateActivity().setShareCount(shareCount + 1);
			userActivityMapper.update(userActivity);
		} else {
			// 多次转发取第一次转发时被引用的动态的值
			shareActivity.setReferActivityId(userActivity.getActivityData().getShareActivity().getReferActivityId());
			UserActivity originalUserActivity =
				userActivityMapper.findById(userActivity.getActivityData().getShareActivity().getReferActivityId());
			shareCount = originalUserActivity.getActivityData().getCreateActivity().getShareCount();
			originalUserActivity.getActivityData().getCreateActivity().setShareCount(shareCount + 1);
			userActivityMapper.update(originalUserActivity);
		}
		ActivityData activityData = new ActivityData();
		activityData.setShareActivity(shareActivity);

		UserActivity shareUserActivity = new UserActivity();
		shareUserActivity.setTop(false);
		shareUserActivity.setUserId(request.getUserId());
		shareUserActivity.setType(ActivityTypeEnum.SHARE_ACTIVITY);
		shareUserActivity.setActivityData(activityData);
		shareUserActivity.setCreateAt(LocalDateTime.now());
		shareUserActivity.setUpdateAt(LocalDateTime.now());
		userActivityMapper.save(shareUserActivity);
		return Response.ok(shareUserActivity);
	}

	@Override
	public Response<UserActivityDto> getUserActivityByComment(GetUserActivityByCommentRequest request, Comment comment) {
		UserActivityDto userActivityDto;
		if (comment.getType().equals(CommentTypeEnum.USER_ACTIVITY)) {
			userActivityDto = userActivityMapper.findCreateActivityById(comment.getAssociateId());
		} else if (comment.getType().equals(CommentTypeEnum.REPLY)) {
			Comment referComment = commentMapper.selectOne(new QueryWrapper<Comment>()
				.select("id", "associate_id")
				.eq("id", comment.getAssociateId()));
			userActivityDto = userActivityMapper.findCreateActivityById(referComment.getAssociateId());
		} else {
			throw new InvalidArgumentException("无效的评论类型");
		}

		userActivityUtil.setUserActivityStatus(userActivityDto, request, true);
		return Response.ok(userActivityDto);
	}
}
