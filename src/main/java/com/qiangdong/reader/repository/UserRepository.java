package com.qiangdong.reader.repository;

import com.qiangdong.reader.search.UserForSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ElasticsearchRepository<UserForSearch, Long> {

}
