package com.qiangdong.reader.controller;


import com.qiangdong.reader.dto.WorksCommentDto;
import com.qiangdong.reader.dto.comic.ComicChapterCommentSummaryDto;
import com.qiangdong.reader.dto.novel.NovelChapterCommentSummaryDto;
import com.qiangdong.reader.dto.comment.CommentDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.entity.UserActivity;
import com.qiangdong.reader.request.comment.*;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
	private final CommentServiceImpl commentService;

	public CommentController(CommentServiceImpl commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/list-novel")
	public PageResponse<WorksCommentDto> listNovelComment(
			@RequestBody ListNovelCommentRequest request) {
		return commentService.listNovelComment(request, new Novel());
	}

	@PostMapping("/publish-novel")
	public Response<String> publishNovelComment(@RequestBody @Valid PublishNovelCommentRequest request) {
		return commentService.publishNovelComment(request, new Novel());
	}

	@PostMapping("/publish-novel-chapter")
	public Response<String> publishNovelChapterComment(@RequestBody @Valid PublishNovelChapterCommentRequest request) {
		return commentService.publishNovelChapterComment(request);
	}

	@PostMapping("/list-comic")
	public PageResponse<WorksCommentDto> listComicComment(@RequestBody ListComicCommentRequest request) {
		return commentService.listComicComment(request, new Comic());
	}

	@PostMapping("/publish-comic")
	public Response<String> publishComicComment(@RequestBody @Valid PublishComicCommentRequest request) {
		return commentService.publishComicComment(request, new Comic());
	}

	@PostMapping("/publish-comic-chapter")
	public Response<String> publishComicChapterComment(@RequestBody @Valid PublishComicChapterCommentRequest request) {
		return commentService.publishComicChapterComment(request);
	}

	@PostMapping("/novel-chapter-summary")
	public PageResponse<NovelChapterCommentSummaryDto> listNovelChapterCommentSummary(
		@RequestBody ListNovelChapterCommentSummaryRequest request) {
		return commentService.listNovelChapterCommentSummary(request);
	}

	@PostMapping("/comic-chapter-summary")
	public PageResponse<ComicChapterCommentSummaryDto> listComicChapterCommentSummary(
		@RequestBody ListComicChapterCommentSummaryRequest request) {
		return commentService.listComicChapterCommentSummary(request);
	}

	@PostMapping("/reply")
	public Response<Comment> replyComment(@RequestBody @Valid ReplyCommentRequest request) {
		return commentService.replyComment(request);
	}

	@PostMapping("/publish-user-activity")
	public Response<Comment> publishUserActivityComment(@RequestBody @Valid PublishUserActivityCommentRequest request) {
		return commentService.publishUserActivityComment(request);
	}

	@PostMapping("/list-user-activity")
	public PageResponse<CommentDto> listUserActivityComment(
		@RequestBody ListUserActivityCommentRequest request) {
		return commentService.listUserActivityComment(request, new UserActivity());
	}

	@PostMapping("/list-reply")
	public PageResponse<CommentDto> listReply(@RequestBody ListReplyRequest request) {
		return commentService.listReply(request, new Comment());
	}

	@PostMapping("/agree")
	public Response<Integer> agreeComment(@RequestBody AgreeCommentRequest request) {
		return commentService.agreeComment(request, new Comment());
	}

	@PostMapping("/against")
	public Response<Integer> againstComment(@RequestBody AgainstCommentRequest request) {
		return commentService.againstComment(request, new Comment());
	}
}
