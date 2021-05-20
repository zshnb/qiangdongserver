package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.BookStandDto;
import com.qiangdong.reader.entity.BookStand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 书架记录 Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@Repository
public interface BookStandMapper extends BaseMapper<BookStand> {
	IPage<BookStandDto> findNovelBookStand(Page<?> page, @Param("userId") Long userId);

	IPage<BookStandDto> findComicBookStand(Page<?> page, @Param("userId") Long userId);

	List<BookStand> findYesterdayAddBookStand(@Param("time") LocalDateTime time, @Param("type") WorksTypeEnum type);
}
