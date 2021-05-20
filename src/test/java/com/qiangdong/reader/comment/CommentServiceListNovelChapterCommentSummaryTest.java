package com.qiangdong.reader.comment;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.novel.NovelChapterCommentSummaryDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comment.ListNovelChapterCommentSummaryRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServiceListNovelChapterCommentSummaryTest extends BaseTest {
	@Autowired
	private CommentServiceImpl commentService;

	@Test
	public void listSuccessful() {
		ListNovelChapterCommentSummaryRequest request = new ListNovelChapterCommentSummaryRequest();
		request.setNovelId(1L);
		PageResponse<NovelChapterCommentSummaryDto> response =
			commentService.listNovelChapterCommentSummary(request);
		assertThat(response.getList().size()).isEqualTo(2);
		assertThat(response.getList().get(0).getChapterId())
			.isGreaterThan(response.getList().get(1).getChapterId());
		response.getList().forEach(it -> {
			assertThat(it.getCommentCount()).isEqualTo(1);
		});
	}

	@Test
	public void listFailedWhenNovelNotExist() {
		ListNovelChapterCommentSummaryRequest request = new ListNovelChapterCommentSummaryRequest();
		request.setNovelId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			commentService.listNovelChapterCommentSummary(request);
		});
	}
}
