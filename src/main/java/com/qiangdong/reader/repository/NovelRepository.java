package com.qiangdong.reader.repository;

import com.qiangdong.reader.search.NovelForSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NovelRepository extends ElasticsearchRepository<NovelForSearch, Long> {

}
