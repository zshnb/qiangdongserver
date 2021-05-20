package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.WithdrawRecordDto;
import com.qiangdong.reader.entity.WithdrawRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-29
 */
@Repository
public interface WithdrawRecordMapper extends BaseMapper<WithdrawRecord> {
    IPage<WithdrawRecordDto> findByUserId(Page<?> page, @Param("userId") Long userId);
}
