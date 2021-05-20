package com.qiangdong.reader.dao;

import com.qiangdong.reader.dto.statistics.DauStatisticsDto;
import com.qiangdong.reader.entity.UserLoginCount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 每日登录数 Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-07
 */
@Repository
public interface UserLoginCountMapper extends BaseMapper<UserLoginCount> {
	Integer countByTime(LocalDateTime time);

	List<DauStatisticsDto> findDauStatisticsByDay();

	List<DauStatisticsDto> findDauStatisticsByMonth();

	List<DauStatisticsDto> findDauStatisticsByYear();
}
