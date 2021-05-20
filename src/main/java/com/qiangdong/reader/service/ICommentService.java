package com.qiangdong.reader.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiangdong.reader.dto.WorksCommentDto;
import com.qiangdong.reader.dto.comic.ComicChapterCommentSummaryDto;
import com.qiangdong.reader.dto.comment.CommentDto;
import com.qiangdong.reader.dto.novel.NovelChapterCommentSummaryDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.request.comment.AgainstCommentRequest;
import com.qiangdong.reader.request.comment.AgreeCommentRequest;
import com.qiangdong.reader.request.comment.ChangeTopRequest;
import com.qiangdong.reader.request.comment.DeleteCommentRequest;
import com.qiangdong.reader.request.comment.ListComicChapterCommentSummaryRequest;
import com.qiangdong.reader.request.comment.ListComicCommentRequest;
import com.qiangdong.reader.request.comment.ListNovelChapterCommentSummaryRequest;
import com.qiangdong.reader.request.comment.ListNovelCommentRequest;
import com.qiangdong.reader.request.comment.ListReplyRequest;
import com.qiangdong.reader.request.comment.ListUserActivityCommentRequest;
import com.qiangdong.reader.request.comment.PublishComicChapterCommentRequest;
import com.qiangdong.reader.request.comment.PublishComicCommentRequest;
import com.qiangdong.reader.request.comment.PublishNovelChapterCommentRequest;
import com.qiangdong.reader.request.comment.PublishNovelCommentRequest;
import com.qiangdong.reader.request.comment.PublishUserActivityCommentRequest;
import com.qiangdong.reader.request.comment.ReplyCommentRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
public interface ICommentService extends IService<Comment> {
    PageResponse<WorksCommentDto> listComicComment(ListComicCommentRequest request, Comic comic);

	PageResponse<WorksCommentDto> listNovelComment(ListNovelCommentRequest request, Novel novel);

	Response<String> publishNovelComment(PublishNovelCommentRequest request, Novel novel);

	@Transactional
	Response<String> publishComicComment(PublishComicCommentRequest request, Comic comic);

	Response<String> publishNovelChapterComment(PublishNovelChapterCommentRequest request);

	@Transactional
	Response<String> publishComicChapterComment(PublishComicChapterCommentRequest request);

    @Transactional(rollbackFor = RuntimeException.class)
    Response<String> deleteComment(DeleteCommentRequest request, Comment comment);

    @Transactional(rollbackFor = RuntimeException.class)
    Response<String> changeTop(ChangeTopRequest request, Comment comment);

    PageResponse<NovelChapterCommentSummaryDto> listNovelChapterCommentSummary(
		ListNovelChapterCommentSummaryRequest request);

	PageResponse<ComicChapterCommentSummaryDto> listComicChapterCommentSummary(
		ListComicChapterCommentSummaryRequest request);

	Response<Comment> replyComment(ReplyCommentRequest request);

	@Transactional(rollbackFor = RuntimeException.class)
	Response<Comment> publishUserActivityComment(PublishUserActivityCommentRequest request);

	PageResponse<CommentDto> listUserActivityComment(ListUserActivityCommentRequest request,
	                                                 UserActivity userActivity);

	PageResponse<CommentDto> listReply(ListReplyRequest request, Comment comment);

	@Transactional
	Response<Integer> agreeComment(AgreeCommentRequest request, Comment comment);

	@Transactional
	Response<Integer> againstComment(AgainstCommentRequest request, Comment comment);
}
