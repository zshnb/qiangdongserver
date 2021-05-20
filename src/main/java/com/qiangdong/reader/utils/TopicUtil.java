package com.qiangdong.reader.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.dao.TopicFollowMapper;
import com.qiangdong.reader.dto.TopicDto;
import com.qiangdong.reader.entity.Topic;
import com.qiangdong.reader.entity.TopicFollow;
import com.qiangdong.reader.repository.TopicRepository;
import com.qiangdong.reader.search.TopicForSearch;
import org.springframework.stereotype.Component;

@Component
public class TopicUtil {
    private final TopicFollowMapper topicFollowMapper;
    private final TopicRepository topicRepository;

    public TopicUtil(TopicFollowMapper topicFollowMapper,
                     TopicRepository topicRepository) {
        this.topicFollowMapper = topicFollowMapper;
        this.topicRepository = topicRepository;
    }

    public void setFollowStatus(TopicDto topic, long userId) {
        boolean isFollow = false;
        TopicFollow topicFollow = topicFollowMapper.selectOne(new QueryWrapper<TopicFollow>()
            .eq("topic_id", topic.getId())
            .eq("user_id", userId));
        if (topicFollow != null) {
            isFollow = true;
        }
        topic.setFollow(isFollow);
    }

    public void indexTopic(Topic topic) {
        TopicForSearch topicForSearch = new TopicForSearch();
        topicForSearch.setId(topic.getId());
        topicForSearch.setName(topic.getName());
        topicRepository.save(topicForSearch);
    }
}
