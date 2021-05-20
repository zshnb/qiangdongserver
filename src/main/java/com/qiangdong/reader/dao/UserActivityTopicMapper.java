package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.Topic;
import com.qiangdong.reader.entity.UserActivityTopic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 话题 Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-16
 */
@Repository
public interface UserActivityTopicMapper extends BaseMapper<UserActivityTopic> {
	void saveAll(List<UserActivityTopic> topics);

	List<Topic> findTopicByActivityId(Long activityId);

	IPage<UserActivityDto> findHeatCreateActivityInTopic(Page<?> page , Long topicId);

	IPage<UserActivityDto> findNewerCreateActivityInTopic(Page<?> page , Long topicId);
}
