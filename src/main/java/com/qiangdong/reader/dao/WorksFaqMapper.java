package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.FaqDto;
import com.qiangdong.reader.entity.WorksFaq;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-23
 */
public interface WorksFaqMapper extends BaseMapper<WorksFaq> {

    IPage<FaqDto> findFaqByUserIdAndCreateAt(Page<?> page,
                                           @Param("userId") Long userId,
                                           @Param("createAt") LocalDateTime createAt);

    Integer findFaqUnReadCountByUserId(Long userId);

    IPage<FaqDto> findSystemFaqByUserId(Page<?> page, Long userId);

    IPage<FaqDto> findFaqyByTypeAndNovel(Page<?> page, Long typeId);

    IPage<FaqDto> findFaqyByTypeAndComic(Page<?> page, Long typeId);
}
