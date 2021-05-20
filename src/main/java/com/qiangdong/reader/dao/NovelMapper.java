package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.novel.NovelAndFirstChapterWithReviewDto;
import com.qiangdong.reader.dto.novel.NovelAndLastUpdateChapterDto;
import com.qiangdong.reader.dto.novel.NovelByManageTypeDto;
import com.qiangdong.reader.dto.novel.NovelDto;
import com.qiangdong.reader.dto.novel.RecommendNovelDto;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.enums.common.WorksUpdateStatusEnum;
import com.qiangdong.reader.search.NovelForSearch;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@Repository
public interface NovelMapper extends BaseMapper<Novel> {
	/**
	 * 根据分类分页获取小说
	 * */
	IPage<NovelDto> findByTypeId(Page<?> page,
	                             @Param("typeId") Long typeId,
	                             @Param("minCount") Integer minCount,
	                             @Param("maxCount") Integer maxCount,
	                             @Param("updateStatus") WorksUpdateStatusEnum updateStatus);

	IPage<NovelDto> findByParentTypeId(Page<?> page,
	                             @Param("typeId") Long typeId,
	                             @Param("minCount") Integer minCount,
	                             @Param("maxCount") Integer maxCount,
	                             @Param("updateStatus") WorksUpdateStatusEnum updateStatus);

	IPage<NovelDto> findByUserId(Page<?> page,
								 @Param("userId") Long userId,
								 @Param("filterUnReviewNovel") boolean filterUnReviewNovel);

	IPage<NovelDto> findNovelNameLike(Page<?> page,
	                                 @Param("novelName") String novelName);

	IPage<NovelAndFirstChapterWithReviewDto> findNovelAndFirstChapter(Page<?> page);

	IPage<NovelByManageTypeDto> findNovelByManageTypeId(Page<?> page,
														@Param("typeId") Long typeId,
														@Param("levelName") String levelName,
														@Param("sex") Integer sex);

	List<RecommendNovelDto> findRecommendNovelByIdIn(List<Long> ids);

	RecommendNovelDto findRecommendNovelById(Long id);

	NovelDto findByNovelId(Long novelId);

	IPage<NovelAndLastUpdateChapterDto> findNovelAndLastChapterByUserId(Page<?> page, @Param("userId") Long userId);

	List<NovelDto> findNovelByWorksTopicId(Long worksTopicId);

	IPage<NovelDto> findDeletedNovels(Page<?> page);

	Novel findDeletedById(Long id);

	void restoreNovel(Long id);

	List<NovelDto> findNovelByTopicId(Long topicId);

	List<NovelForSearch> findAllForSearch();
}
