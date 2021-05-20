package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.read_activity_record.ActivityReadHistoryDto;
import com.qiangdong.reader.entity.ActivityReadHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-17
 */
@Repository
public interface ActivityReadHistoryMapper extends BaseMapper<ActivityReadHistory> {

	IPage<ActivityReadHistoryDto> findActivityReadHistoryByUserId(Page<?> page, Long userId);
}
