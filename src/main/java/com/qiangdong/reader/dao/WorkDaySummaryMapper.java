package com.qiangdong.reader.dao;

import com.qiangdong.reader.entity.WorkDaySummary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-05
 */
@Repository
public interface WorkDaySummaryMapper extends BaseMapper<WorkDaySummary> {
	void saveAll(List<WorkDaySummary> workDaySummaries);

	List<WorkDaySummary> findCurrentMonthWorkDaySummary(Long userId);
}
