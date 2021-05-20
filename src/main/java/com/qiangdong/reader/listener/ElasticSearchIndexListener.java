package com.qiangdong.reader.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.common.ComicConstant;
import com.qiangdong.reader.common.ElasticSearchIndexConstant;
import com.qiangdong.reader.common.NovelConstant;
import com.qiangdong.reader.common.UserActivityConstant;
import com.qiangdong.reader.common.UserConstant;
import com.qiangdong.reader.config.ElasticSearchConfig;
import com.qiangdong.reader.dao.ComicMapper;
import com.qiangdong.reader.dao.NovelMapper;
import com.qiangdong.reader.dao.TopicMapper;
import com.qiangdong.reader.dao.UserActivityMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.entity.Topic;
import com.qiangdong.reader.repository.ComicRepository;
import com.qiangdong.reader.repository.NovelRepository;
import com.qiangdong.reader.repository.TopicRepository;
import com.qiangdong.reader.repository.UserActivityRepository;
import com.qiangdong.reader.repository.UserRepository;
import com.qiangdong.reader.search.ComicForSearch;
import com.qiangdong.reader.search.NovelForSearch;
import com.qiangdong.reader.search.TopicForSearch;
import com.qiangdong.reader.search.UserActivityForSearch;
import com.qiangdong.reader.search.UserForSearch;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

@Component
public class ElasticSearchIndexListener implements ApplicationListener<ApplicationReadyEvent> {
    private final ElasticsearchTemplate elasticsearchTemplate;
    private final ElasticSearchConfig elasticSearchConfig;
    private final NovelRepository novelRepository;
    private final ComicRepository comicRepository;
    private final NovelMapper novelMapper;
    private final ComicMapper comicMapper;
    private final UserMapper userMapper;
    private final TopicMapper topicMapper;
    private final UserActivityMapper userActivityMapper;
    private final UserActivityRepository userActivityRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public ElasticSearchIndexListener(ElasticsearchTemplate elasticsearchTemplate,
                                      ElasticSearchConfig elasticSearchConfig,
                                      NovelRepository novelRepository,
                                      ComicRepository comicRepository, NovelMapper novelMapper,
                                      ComicMapper comicMapper,
                                      UserMapper userMapper,
                                      TopicMapper topicMapper,
                                      UserActivityMapper userActivityMapper,
                                      UserActivityRepository userActivityRepository,
                                      UserRepository userRepository,
                                      TopicRepository topicRepository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.elasticSearchConfig = elasticSearchConfig;
        this.novelRepository = novelRepository;
        this.comicRepository = comicRepository;
        this.novelMapper = novelMapper;
        this.comicMapper = comicMapper;
        this.userMapper = userMapper;
        this.topicMapper = topicMapper;
        this.userActivityMapper = userActivityMapper;
        this.userActivityRepository = userActivityRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (elasticSearchConfig.isIndexNovel()) {
            elasticsearchTemplate.deleteIndex(NovelForSearch.class);
            elasticsearchTemplate.createIndex(ElasticSearchIndexConstant.NOVEL_INDEX_NAME);
            elasticsearchTemplate.putMapping(NovelForSearch.class);
            novelRepository.saveAll(novelMapper.findAllForSearch());
        }

        if (elasticSearchConfig.isIndexComic()) {
            elasticsearchTemplate.deleteIndex(ComicForSearch.class);
            elasticsearchTemplate.createIndex(ElasticSearchIndexConstant.COMIC_INDEX_NAME);
            elasticsearchTemplate.putMapping(ComicForSearch.class);
            comicRepository.saveAll(comicMapper.findAllForSearch());
        }

        if (elasticSearchConfig.isIndexUserActivity()) {
            elasticsearchTemplate.deleteIndex(UserActivityForSearch.class);
            elasticsearchTemplate.createIndex(ElasticSearchIndexConstant.USER_ACTIVITY_INDEX_NAME);
            elasticsearchTemplate.putMapping(UserActivityForSearch.class);
            List<UserActivityForSearch> userActivityForSearches = userActivityMapper.findAllForSearch();
            userActivityRepository.saveAll(userActivityForSearches);
        }

        if (elasticSearchConfig.isIndexUser()) {
            elasticsearchTemplate.deleteIndex(UserForSearch.class);
            elasticsearchTemplate.createIndex(ElasticSearchIndexConstant.USER_INDEX_NAME);
            elasticsearchTemplate.putMapping(UserForSearch.class);
            userRepository.saveAll(userMapper.findAllForSearch());
        }

        if (elasticSearchConfig.isIndexTopic()) {
            elasticsearchTemplate.deleteIndex(TopicForSearch.class);
            elasticsearchTemplate.createIndex(ElasticSearchIndexConstant.TOPIC_INDEX_NAME);
            elasticsearchTemplate.putMapping(TopicForSearch.class);
            List<TopicForSearch> topicForSearches = topicMapper.selectList(new QueryWrapper<>()).stream().map(topic -> {
                TopicForSearch topicForSearch = new TopicForSearch();
                topicForSearch.setId(topic.getId());
                topicForSearch.setName(topic.getName());
                return topicForSearch;
            }).collect(Collectors.toList());
            topicRepository.saveAll(topicForSearches);
        }
    }
}
