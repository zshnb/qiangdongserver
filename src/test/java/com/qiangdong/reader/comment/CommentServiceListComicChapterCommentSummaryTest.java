package com.qiangdong.reader.comment;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.comic.ComicChapterCommentSummaryDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comment.ListComicChapterCommentSummaryRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServiceListComicChapterCommentSummaryTest extends BaseTest {
	@Autowired
	private CommentServiceImpl commentService;

	@Test
	public void listSuccessful() {
		ListComicChapterCommentSummaryRequest request = new ListComicChapterCommentSummaryRequest();
		request.setComicId(1L);
		PageResponse<ComicChapterCommentSummaryDto> response =
			commentService.listComicChapterCommentSummary(request);
		assertThat(response.getList().size()).isEqualTo(2);
		assertThat(response.getList().get(0).getChapterId())
			.isGreaterThan(response.getList().get(1).getChapterId());
		response.getList().forEach(it -> {
			assertThat(it.getCommentCount()).isEqualTo(1);
		});
	}

	@Test
	public void listFailedWhenNovelNotExist() {
		ListComicChapterCommentSummaryRequest request = new ListComicChapterCommentSummaryRequest();
		request.setComicId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			commentService.listComicChapterCommentSummary(request);
		});
	}
}
