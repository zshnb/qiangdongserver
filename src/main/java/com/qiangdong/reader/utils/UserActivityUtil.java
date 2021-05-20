package com.qiangdong.reader.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.common.UserActivityConstant;
import com.qiangdong.reader.common.UserConstant;
import com.qiangdong.reader.dao.FollowRelationMapper;
import com.qiangdong.reader.dao.UserActivityCollectionMapper;
import com.qiangdong.reader.dao.UserActivityTopicMapper;
import com.qiangdong.reader.dto.user_activity.ActivityData;
import com.qiangdong.reader.dto.user_activity.CreateActivity;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.FollowRelation;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.entity.UserActivityCollection;
import com.qiangdong.reader.repository.UserActivityRepository;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.search.UserActivityForSearch;
import java.util.List;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class UserActivityUtil {
    private final UserActivityTopicMapper userActivityTopicMapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final UserActivityCollectionMapper userActivityCollectionMapper;
    private final FollowRelationMapper followRelationMapper;
    private final UserActivityRepository userActivityRepository;
    private final RedisTemplate<String, List<Long>> userWithAgreeActivityRedisTemplate;
    private final RedisTemplate<String, CreateActivity> createActivityRedisTemplate;

    public UserActivityUtil(UserActivityTopicMapper userActivityTopicMapper,
                            RedisTemplate<String, String> redisTemplate,
                            UserActivityCollectionMapper userActivityCollectionMapper,
                            FollowRelationMapper followRelationMapper,
                            UserActivityRepository userActivityRepository,
                            RedisTemplate<String, List<Long>> userWithAgreeActivityRedisTemplate,
                            RedisTemplate<String, CreateActivity> createActivityRedisTemplate) {
        this.userActivityTopicMapper = userActivityTopicMapper;
        this.redisTemplate = redisTemplate;
        this.userActivityCollectionMapper = userActivityCollectionMapper;
        this.followRelationMapper = followRelationMapper;
        this.userActivityRepository = userActivityRepository;
        this.userWithAgreeActivityRedisTemplate = userWithAgreeActivityRedisTemplate;
        this.createActivityRedisTemplate = createActivityRedisTemplate;
    }

    /**
     * 设置动态返回的点赞反对，收藏，发送者的关注状态值
     * */
    public void setUserActivityStatus(UserActivityDto userActivityDto, BaseRequest request, boolean needSetFollow) {
        HashOperations<String, Long, List<Long>> userWithAgreeActivityHashOperation =
            userWithAgreeActivityRedisTemplate.opsForHash();
        String key = String.format(UserConstant.KEY_USER_AGREE_ACTIVITY, request.getUserId() % 10);
        List<Long> userActivityIds = userWithAgreeActivityHashOperation.get(key, request.getUserId());
        if (!CollectionUtils.isEmpty(userActivityIds) && userActivityIds.contains(userActivityDto.getId())) {
            userActivityDto.setAgree(true);
        }

        HashOperations<String, Long, CreateActivity> hashOperations = createActivityRedisTemplate.opsForHash();
        key = String.format(UserActivityConstant.KEY_USER_ACTIVITY_AGREE_AGAINST, userActivityDto.getId() % 10);
        CreateActivity redisCreateActivity = hashOperations.get(key, userActivityDto.getId());
        int agreeCount;
        if (redisCreateActivity == null) {
            agreeCount = 0;
        } else {
            agreeCount = redisCreateActivity.getAgreeCount();
        }
        ActivityData activityData = userActivityDto.getActivityData();
        CreateActivity createActivity = activityData.getCreateActivity();
        createActivity.setAgreeCount(agreeCount);

        userActivityDto.setTopics(userActivityTopicMapper.findTopicByActivityId(userActivityDto.getId()));

        UserActivityCollection collection =
            userActivityCollectionMapper.selectOne(new QueryWrapper<UserActivityCollection>()
                .select("id")
                .eq("user_id", request.getUserId())
                .eq("user_activity_id", userActivityDto.getId()));
        if (collection != null) {
            userActivityDto.setInCollection(true);
        }
        if (needSetFollow) {
            FollowRelation followRelation = followRelationMapper.selectOne(new QueryWrapper<FollowRelation>()
                .select("id")
                .eq("follower_id", request.getUserId())
                .eq("followed_id", userActivityDto.getUserId()));
            if (followRelation != null) {
                userActivityDto.setFollow(true);
            }
        }
    }

    public void indexUserActivity(UserActivity userActivity, User user) {
        UserActivityForSearch userActivityForSearch = new UserActivityForSearch();
        userActivityForSearch.setId(userActivity.getId());
        userActivityForSearch.setUserId(user.getId());
        userActivityForSearch.setUsername(user.getUsername());
        userActivityForSearch.setAvatar(user.getAvatar());
        userActivityForSearch.setCreateActivity(userActivity.getActivityData().getCreateActivity());
        userActivityForSearch.setTop(userActivity.getTop());
        userActivityForSearch.setCreateAt(userActivity.getCreateAt());
        userActivityRepository.save(userActivityForSearch);
    }
}
