package com.qiangdong.reader.repository;

import com.qiangdong.reader.search.UserActivityForSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityRepository extends ElasticsearchRepository<UserActivityForSearch, Long> {

}
