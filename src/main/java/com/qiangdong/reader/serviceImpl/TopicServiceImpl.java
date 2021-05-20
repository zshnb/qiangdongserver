package com.qiangdong.reader.serviceImpl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiangdong.reader.common.TopicConstant;
import com.qiangdong.reader.dao.TopicFollowMapper;
import com.qiangdong.reader.dao.TopicMapper;
import com.qiangdong.reader.dao.UserActivityTopicMapper;
import com.qiangdong.reader.dto.TopicDto;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.Topic;
import com.qiangdong.reader.entity.TopicFollow;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.request.topic.AddOrUpdateTopicRequest;
import com.qiangdong.reader.request.topic.FollowOrUnFollowTopicRequest;
import com.qiangdong.reader.request.topic.GetTopicRequest;
import com.qiangdong.reader.request.topic.ListHeatUserActivityRequest;
import com.qiangdong.reader.request.topic.ListNewestUserActivityRequest;
import com.qiangdong.reader.request.topic.ListSimilarTopicRequest;
import com.qiangdong.reader.request.topic.SearchTopicRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.search.TopicForSearch;
import com.qiangdong.reader.service.ITopicService;
import com.qiangdong.reader.utils.AssertUtil;
import com.qiangdong.reader.utils.PageUtil;
import com.qiangdong.reader.utils.TopicUtil;
import com.qiangdong.reader.utils.UserActivityUtil;
import java.util.List;
import java.util.Set;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 用户协议 服务实现类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-16
 */
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements ITopicService {
	private final TopicFollowMapper topicFollowMapper;
	private final TopicMapper topicMapper;
	private final UserActivityTopicMapper userActivityTopicMapper;
	private final PageUtil pageUtil;
	private final UserActivityUtil userActivityUtil;
	private final TopicUtil topicUtil;
	private final ElasticsearchTemplate elasticsearchTemplate;
	private final RedisTemplate<String, Topic> redisTemplate;

	public TopicServiceImpl(TopicFollowMapper topicFollowMapper,
	                        TopicMapper topicMapper,
	                        UserActivityTopicMapper userActivityTopicMapper, PageUtil pageUtil,
	                        UserActivityUtil userActivityUtil, TopicUtil topicUtil,
	                        ElasticsearchTemplate elasticsearchTemplate,
	                        RedisTemplate<String, Topic> redisTemplate) {
		this.topicFollowMapper = topicFollowMapper;
		this.topicMapper = topicMapper;
		this.userActivityTopicMapper = userActivityTopicMapper;
		this.pageUtil = pageUtil;
		this.userActivityUtil = userActivityUtil;
		this.topicUtil = topicUtil;
		this.elasticsearchTemplate = elasticsearchTemplate;
		this.redisTemplate = redisTemplate;
	}

	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Response<Topic> addOrUpdateTopic(@RequestBody AddOrUpdateTopicRequest request) {
	    Topic topic;
	    if (request.getTopicId() == 0L) {
		    topic = getOne(new QueryWrapper<Topic>()
			    .eq("name", request.getName()));
		    AssertUtil.assertNull(topic, "话题已存在");

		    topic = new Topic();
		    topic.setName(request.getName());
		    topic.setCover(request.getCover());
		    topic.setUserId(request.getUserId());
		    save(topic);
	    } else {
	    	topic = getById(request.getTopicId());
	    	AssertUtil.assertNotNull(topic, "话题不存在");
	    	topic.setName(request.getName());
	    	topic.setCover(request.getCover());
	    	updateById(topic);
	    }
	    topicUtil.indexTopic(topic);
		return Response.ok(topic);
	}

	@Override
	public Response<TopicDto> getTopic(GetTopicRequest request) {
		TopicDto topic = topicMapper.findById(request.getTopicId());
		AssertUtil.assertNotNull(topic, "话题不存在");

		topicUtil.setFollowStatus(topic, request.getUserId());
		return Response.ok(topic);
	}

	@Override
	public PageResponse<Topic> listTopic(BaseRequest request) {
		return PageResponse.of(page(pageUtil.of(request), new QueryWrapper<Topic>()
			.orderByDesc("create_at")), request.getPageSize());
	}

	@Override
	public PageResponse<TopicForSearch> searchTopic(SearchTopicRequest request) {
		QueryBuilder queryBuilder = QueryBuilders.matchQuery("name", request.getName());

		Pageable pageable = PageRequest.of((int)(request.getPageNumber() - 1), request.getPageSize().intValue());
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
			.withQuery(queryBuilder)
			.withPageable(pageable)
			.build();
		Page<TopicForSearch> page = elasticsearchTemplate.queryForPage(searchQuery, TopicForSearch.class);

		return PageResponse.of(page.getContent(), request.getPageSize(), page.getTotalElements());
	}

	@Override
	public PageResponse<Topic> listHotTopic(BaseRequest request) {
		ZSetOperations<String, Topic> zSetOperations = redisTemplate.opsForZSet();
		Set<Topic> topics = zSetOperations.reverseRangeByScore(TopicConstant.KEY_HOT_TOPIC, 0.0, Double.MAX_VALUE);
		if (!CollectionUtils.isEmpty(topics)) {
			long start = (request.getPageNumber() - 1) * request.getPageSize();
			if (start >= topics.size()) {
				start = 0;
			}
			long end = start + request.getPageSize();
			return PageResponse.of(CollectionUtil.sub(topics, (int)start, (int)end), request.getPageSize(),
				Integer.toUnsignedLong(topics.size()));
		}
		return PageResponse.of();
	}
	/**
	 * 返回最新的5个话题
	 * */
	@Override
	public PageResponse<TopicDto> listNewestTopic(BaseRequest request) {
		List<Topic> topics = list(new QueryWrapper<Topic>()
			.orderByDesc("create_at")
			.last("limit 5"));

		List<Long> topicFollows = topicFollowMapper.selectList(new QueryWrapper<TopicFollow>()
			.eq("user_id", request.getUserId())
			.in("topic_id", topics.stream().map(Topic::getId).collect(Collectors.toList())))
			.stream()
			.map(TopicFollow::getTopicId)
			.collect(Collectors.toList());

		List<TopicDto> topicDtos = topics.stream().map(t -> {
			TopicDto topicDto = new TopicDto();
			BeanUtils.copyProperties(t, topicDto);
			if (topicFollows.contains(t.getId())) {
				topicDto.setFollow(true);
			} else {
				topicDto.setFollow(false);
			}
			return topicDto;
		}).collect(Collectors.toList());
		return PageResponse.of(topicDtos, request.getPageSize());
	}


	@Override
	@Transactional
	public Response<String> followTopic(FollowOrUnFollowTopicRequest request, Topic topic) {
		TopicFollow topicFollow = topicFollowMapper.selectOne(new QueryWrapper<TopicFollow>()
			.eq("topic_id", topic.getId())
			.eq("user_id", request.getUserId()));
		AssertUtil.assertNull(topicFollow, "已关注话题");
		topicFollow = new TopicFollow();
		topicFollow.setUserId(request.getUserId());
		topicFollow.setTopicId(request.getTopicId());
		topicFollowMapper.insert(topicFollow);

		topic.setClickCount(topic.getClickCount() + 1);
		updateById(topic);
		return Response.ok();
	}

	@Override
    @Transactional
	public Response<String> unFollowTopic(FollowOrUnFollowTopicRequest request, Topic topic) {
		TopicFollow topicFollow = topicFollowMapper.selectOne(new QueryWrapper<TopicFollow>()
			.eq("topic_id", topic.getId())
			.eq("user_id", request.getUserId()));
		AssertUtil.assertNotNull(topicFollow, "未关注话题");
		topicFollowMapper.deleteById(topicFollow.getId());

		topic.setClickCount(topic.getClickCount() - 1);
		updateById(topic);
		return Response.ok();
	}

	/**
	 * 相似话题
	 * */
	@Override
	public PageResponse<TopicDto> listSimilarTopic(ListSimilarTopicRequest request) {
		IPage<Topic> topicIPage = page(pageUtil.of(request), new QueryWrapper<Topic>()
			.ne("name", request.getName()));
		List<Topic> topics = topicIPage.getRecords();

		List<Long> topicFollows = topicFollowMapper.selectList(new QueryWrapper<TopicFollow>()
			.eq("user_id", request.getUserId())
			.in("topic_id", topics.stream().map(Topic::getId).collect(Collectors.toList())))
			.stream()
			.map(TopicFollow::getTopicId)
			.collect(Collectors.toList());

		List<TopicDto> topicDtos = topics.stream().map(t -> {
			TopicDto topicDto = new TopicDto();
			BeanUtils.copyProperties(t, topicDto);
			if (topicFollows.contains(t.getId())) {
				topicDto.setFollow(true);
			} else {
				topicDto.setFollow(false);
			}
			return topicDto;
		}).collect(Collectors.toList());
		return PageResponse.of(topicDtos, topicIPage.getTotal(), request.getPageSize());
	}

	/**
	 * 话题热门动态
	 * */
	@Override
	public PageResponse<UserActivityDto> listHeatUserActivity(ListHeatUserActivityRequest request, Topic topic) {
	    IPage<UserActivityDto> userActivityDtoIPage = userActivityTopicMapper.findHeatCreateActivityInTopic(
			pageUtil.of(request), request.getTopicId());
	    List<UserActivityDto> userActivityDtos = userActivityDtoIPage.getRecords();
	    userActivityDtos.forEach(it -> userActivityUtil.setUserActivityStatus(it, request, true));

		return PageResponse.of(userActivityDtos, userActivityDtoIPage.getTotal(), request.getPageSize());
	}

	/**
	 * 话题最新动态
	 * */
	@Override
	public PageResponse<UserActivityDto> listNewestUserActivity(ListNewestUserActivityRequest request, Topic topic) {
		IPage<UserActivityDto> userActivityDtoIPage = userActivityTopicMapper.findNewerCreateActivityInTopic(
			pageUtil.of(request), request.getTopicId());
		List<UserActivityDto> userActivityDtos = userActivityDtoIPage.getRecords();
		userActivityDtos.forEach(it -> userActivityUtil.setUserActivityStatus(it, request, true));

		return PageResponse.of(userActivityDtos, userActivityDtoIPage.getTotal(), request.getPageSize());
	}
}
