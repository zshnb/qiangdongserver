package com.qiangdong.reader.controller.manage;


import com.qiangdong.reader.dto.WorksCommentDto;
import com.qiangdong.reader.dto.comic.ComicChapterCommentSummaryDto;
import com.qiangdong.reader.dto.novel.NovelChapterCommentSummaryDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.request.comment.DeleteCommentRequest;
import com.qiangdong.reader.request.comment.ListComicChapterCommentSummaryRequest;
import com.qiangdong.reader.request.comment.ListComicCommentRequest;
import com.qiangdong.reader.request.comment.ListNovelChapterCommentSummaryRequest;
import com.qiangdong.reader.request.comment.ListNovelCommentRequest;
import com.qiangdong.reader.request.comment.ReplyCommentRequest;
import com.qiangdong.reader.request.comment.ChangeTopRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/manage/comment")
public class ManageCommentController {
	@Autowired
	private CommentServiceImpl commentService;

	@PostMapping("/list-novel")
	public PageResponse<WorksCommentDto> listNovelComment(
			@RequestBody ListNovelCommentRequest request) {
		return commentService.listNovelComment(request, new Novel());
	}

	@PostMapping("/list-comic")
	public PageResponse<WorksCommentDto> listComicComment(
			@RequestBody ListComicCommentRequest request) {
		return commentService.listComicComment(request, new Comic());
	}

	@DeleteMapping("/{commentId}")
	public Response<String> deleteComment(@RequestBody DeleteCommentRequest request) {
		return commentService.deleteComment(request, new Comment());
	}

	@PostMapping("/change-top")
	public Response<String> topComment(@RequestBody ChangeTopRequest request) {
		return commentService.changeTop(request, new Comment());
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
	public Response<Comment> replyComment(@RequestBody ReplyCommentRequest request) {
		return commentService.replyComment(request);
	}
}
