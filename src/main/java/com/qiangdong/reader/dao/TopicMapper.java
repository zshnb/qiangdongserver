package com.qiangdong.reader.dao;

import com.qiangdong.reader.dto.TopicDto;
import com.qiangdong.reader.entity.Topic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户协议 Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-16
 */
@Repository
public interface TopicMapper extends BaseMapper<Topic> {
	void increaseReferenceCount(List<Long> topicIds);
	void decreaseReferenceCount(List<Long> topicIds);
	TopicDto findById(Long id);
}
