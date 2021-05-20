package com.qiangdong.reader.repository;

import com.qiangdong.reader.search.TopicForSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends ElasticsearchRepository<TopicForSearch, Long> {

}
