package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.comic.ComicChapterCatalogItemDto;
import com.qiangdong.reader.dto.comic.ComicChapterDto;
import com.qiangdong.reader.entity.ComicChapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qiangdong.reader.entity.NovelChapter;
import java.time.LocalDateTime;
import org.apache.ibatis.annotations.Param;
import java.util.List;
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
public interface ComicChapterMapper extends BaseMapper<ComicChapter> {
	List<ComicChapter> findChaptersByComicId(Long comicId);

	ComicChapterDto findLastUpdateChapter(Long comicId);

	IPage<ComicChapterCatalogItemDto> findComicChapterCatalogItemByComicId(Page<?> page, @Param("comicId")Long comicId);

	Integer findLastIndexByComicId(Long comicId);

	IPage<ComicChapterDto> findDeleteChapters(Page<?> page, @Param("comicId") Long comicId);

	ComicChapterDto findDeleteChapterById(Long chapterId);

	List<ComicChapter> findYesterdayUpdate(LocalDateTime time);
}
