package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.UserCreditRecordDto;
import com.qiangdong.reader.dto.statistics.CreditStatisticsDto;
import com.qiangdong.reader.dto.statistics.CreditUserStatisticsDto;
import com.qiangdong.reader.dto.statistics.UserCreditRankDto;
import com.qiangdong.reader.entity.UserCreditRecord;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 虚拟货币充值记录 Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@Repository
public interface UserCreditRecordMapper extends BaseMapper<UserCreditRecord> {
    IPage<UserCreditRecordDto> listUserCreditRecord(Page<?> page, @Param("username") String username);

    BigDecimal sumByTime(LocalDateTime time);

    Integer countCreditUserByTime(LocalDateTime time);

    BigDecimal sumCredit();

    List<CreditStatisticsDto> findCreditStatisticsByDay();

    List<CreditStatisticsDto> findCreditStatisticsByMonth();

    List<CreditStatisticsDto> findCreditStatisticsByYear();

    List<CreditUserStatisticsDto> findCreditUserStatisticsByDay();

    List<CreditUserStatisticsDto> findCreditUserStatisticsByMonth();

    List<CreditUserStatisticsDto> findCreditUserStatisticsByYear();

    IPage<UserCreditRankDto> findUserCreditUserRank(Page<?> page);
}
