package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.WorksIncomeUserRankingDto;
import com.qiangdong.reader.entity.UserConsumption;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.enums.user_consumption.ConsumptionTypeEnum;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-22
 */
@Repository
public interface UserConsumptionMapper extends BaseMapper<UserConsumption> {
	IPage<WorksIncomeUserRankingDto> findNovelIncomeUserRankingDtoById(Page<?> page,
	                                                                   @Param("novelId") Long novelId,
	                                                                   @Param("type") Integer type);
	IPage<WorksIncomeUserRankingDto> findComicIncomeUserRankingDtoById(Page<?> page,
	                                                                   @Param("comicId") Long comicId,
	                                                                   @Param("type") Integer type);

	List<UserConsumption> findYesterdayNovelRecommendTicket(@Param("time") LocalDateTime time,
	                                                        @Param("type")ConsumptionTypeEnum type);

	List<UserConsumption> findYesterdayComicRecommendTicket(@Param("time") LocalDateTime time,
	                                                        @Param("type")ConsumptionTypeEnum type);
}
