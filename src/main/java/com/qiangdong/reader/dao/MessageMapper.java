package com.qiangdong.reader.dao;

import com.qiangdong.reader.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 消息 Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-31
 */
@Repository
public interface MessageMapper extends BaseMapper<Message> {

}
