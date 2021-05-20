package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.WorksReadHistoryDto;
import com.qiangdong.reader.dto.statistics.ReadUserAgeWithCount;
import com.qiangdong.reader.dto.statistics.ReadUserSexWithCount;
import com.qiangdong.reader.entity.WorksReadHistory;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-20
 */
@Repository
public interface WorksReadHistoryMapper extends BaseMapper<WorksReadHistory> {
	IPage<WorksReadHistoryDto> findNovelHistoryDto(Page<?> page, @Param("userId") Long userId);
	IPage<WorksReadHistoryDto> findComicHistoryDto(Page<?> page, @Param("userId") Long userId);

	List<ReadUserSexWithCount> findReadUserSexWithCount(@Param("worksId") Long worksId,
	                                                    @Param("worksType") WorksTypeEnum worksType);

	List<ReadUserAgeWithCount> findReadUserAgeWithCount(@Param("worksId") Long worksId,
	                                                    @Param("worksType") WorksTypeEnum worksType);
}
