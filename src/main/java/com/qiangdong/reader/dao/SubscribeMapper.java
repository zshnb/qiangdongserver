package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.SubscribeDto;
import com.qiangdong.reader.entity.Subscribe;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.response.PageResponse;
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
public interface SubscribeMapper extends BaseMapper<Subscribe> {
	Integer findYesterdaySubscribeCount(@Param("worksId") Long worksId, @Param("worksType") Integer type);

	IPage<SubscribeDto> findByNovelAndPostTime(Page<?> page,
											   @Param("novelId") Long novelId,
											   @Param("postTime") String postTime);

	IPage<SubscribeDto> findByComicAndPostTime(Page<?> page,
											   @Param("comicId") Long comicId,
											   @Param("postTime") String postTime);

	Integer countMaxChapterSubscribe(@Param("worksId") Long worksId,
								   @Param("worksType") Integer worksType);

    Integer sumSubscribeByWorks(@Param("worksId") Long worksId,
                                @Param("worksType") Integer worksType,
                                @Param("time") LocalDateTime createAt);

    List<SubscribeDto> findByWorksAndTime(@Param("worksId") Long worksId,
                                          @Param("worksType") Integer worksType,
                                          @Param("time") LocalDateTime createAt);

    List<Subscribe> findYesterdaySubscribeByWorksType(@Param("time") LocalDateTime time,
                                                      @Param("worksType") WorksTypeEnum worksType);
}
