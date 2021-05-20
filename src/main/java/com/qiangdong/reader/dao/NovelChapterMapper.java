package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.novel.NovelChapterCatalogItemDto;
import com.qiangdong.reader.dto.novel.NovelChapterDto;
import com.qiangdong.reader.entity.NovelChapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiangdong.reader.entity.WorkDaySummary;
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
 * @since 2020-05-28
 */
@Repository
public interface NovelChapterMapper extends BaseMapper<NovelChapter> {
	List<NovelChapter> findChaptersByNovelId(Long novelId);

	NovelChapterDto findLastUpdateChapter(Long novelId);

	IPage<NovelChapterCatalogItemDto> findNovelChapterCatalogItemByNovelId(Page<?> page, @Param("novelId")Long novelId);

	List<NovelChapter> findCurrentMonthChapterByNovelId(Long novelId);

	Integer findLastIndexByNovelId(Long novelId);

	IPage<NovelChapterDto> findDeleteChapters(Page<?> page, @Param("novelId") Long novelId);

	NovelChapterDto findDeleteChapterById(Long chapterId);

	List<WorkDaySummary> findYesterdayWriteWordCount(@Param("ids") List<Long> ids, @Param("time") LocalDateTime time);

	List<NovelChapter> findYesterdayUpdate(LocalDateTime time);

	void saveAll(List<NovelChapter> novelChapters);
}
