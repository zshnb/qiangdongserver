package com.qiangdong.reader.repository;

import com.qiangdong.reader.search.ComicForSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComicRepository extends ElasticsearchRepository<ComicForSearch, Long> {

}
