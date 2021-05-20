package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiangdong.reader.entity.Notice;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 公告表 Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-04
 */
@Repository
public interface NoticeMapper extends BaseMapper<Notice> {
	List<Notice> findByIdIn(List<Long> ids);
}
