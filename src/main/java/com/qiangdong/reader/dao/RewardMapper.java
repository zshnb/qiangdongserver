package com.qiangdong.reader.dao;

import com.qiangdong.reader.dto.RewardDto;
import com.qiangdong.reader.entity.Reward;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@Repository
public interface RewardMapper extends BaseMapper<Reward> {
    Integer sumRewardByWorksAndTime(@Param("worksId") Long worksId,
                                    @Param("worksType") Integer worksType,
                                    @Param("time") LocalDateTime time);

    List<RewardDto> findByWorksAndTime(@Param("worksId") Long worksId,
                                       @Param("worksType") Integer worksType,
                                       @Param("time") LocalDateTime time);

    List<Reward> findYesterdayRewardByWorksType(@Param("time") LocalDateTime time,
                                                @Param("worksType")WorksTypeEnum worksType);
}
