package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.comic.ComicAndFirstChapterWithReviewDto;
import com.qiangdong.reader.dto.comic.ComicAndLastUpdateChapterDto;
import com.qiangdong.reader.dto.comic.ComicByManageTypeDto;
import com.qiangdong.reader.dto.comic.ComicDto;
import com.qiangdong.reader.dto.comic.RecommendComicDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.search.ComicForSearch;
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
public interface ComicMapper extends BaseMapper<Comic> {
	/**
	 * 根据分类分页获取小说
	 * */
	IPage<ComicDto> findByTypeId(Page<?> page, @Param("typeId") Long typeId);

	IPage<ComicDto> findByUserId(Page<?> page,
								 @Param("userId") Long userId,
								 @Param("filterNoReviewComic") boolean filterNoReviewComic);

	IPage<ComicDto> findComicNameLike(Page<?> page, @Param("comicName") String comicName);

	IPage<ComicByManageTypeDto> findComicByManageType(Page<?> page,
													  @Param("typeId") Long typeId,
													  @Param("levelName") String levelName,
													  @Param("sex") Integer sex);

	List<ComicDto> findByComicIdIn(List<Long> ids);

	List<RecommendComicDto> findRecommendNovelByIdIn(List<Long> ids);

	RecommendComicDto findRecommendComicById(Long id);

	ComicDto findByComicId(Long comicId);

	IPage<ComicAndFirstChapterWithReviewDto> findComicAndFirstChapter(Page<?> page);

	IPage<ComicAndLastUpdateChapterDto> findComicAndLastChapterByUserId(Page<?> page, @Param("userId") Long userId);

	List<ComicDto> findComicByWorksTopicId(Long worksTopicId);

	IPage<ComicDto> findDeletedComics(Page<?> page);

	Comic findDeletedById(Long id);

	void restoreComic(Long id);

	List<ComicDto> findComicByTopicId(Long topicId);

	List<ComicForSearch> findAllForSearch();
}
