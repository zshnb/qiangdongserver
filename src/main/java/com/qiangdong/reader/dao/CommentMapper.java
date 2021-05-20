package com.qiangdong.reader.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiangdong.reader.dto.WorksCommentDto;
import com.qiangdong.reader.dto.comic.ComicChapterCommentSummaryDto;
import com.qiangdong.reader.dto.novel.NovelChapterCommentSummaryDto;
import com.qiangdong.reader.dto.comment.CommentDto;
import com.qiangdong.reader.entity.Comment;
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
public interface CommentMapper extends BaseMapper<Comment> {
	IPage<WorksCommentDto> findNovelCommentByNovelId(Page<?> page, @Param("novelId") Long novelId);

	IPage<WorksCommentDto> findComicCommentByComicId(Page<?> page, @Param("comicId") Long comicId);

	IPage<CommentDto> findUserActivityCommentByUserActivityId(Page<?> page,
	                                                          @Param("activityId") Long activityId);

	IPage<CommentDto> findReplyCommentByCommentId(Page<?> page, @Param("commentId") Long commentId);

	List<CommentDto> findCreateActivityComment(List<Long> ids);

	List<CommentDto> findByIdIn(List<Long> ids);

	List<NovelChapterCommentSummaryDto> findNovelChapterSummaryByNovelId(List<Long> chapterIds);

	List<ComicChapterCommentSummaryDto> findComicChapterSummaryByNovelId(List<Long> chapterIds);
}
