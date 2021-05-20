package com.qiangdong.reader.comment;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.WorksCommentDto;
import com.qiangdong.reader.entity.Comic;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comment.ListComicCommentRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServiceListComicCommentTest extends BaseTest {
	@Autowired
	private CommentServiceImpl commentService;

	@Test
	public void listComicCommentSuccessful() {
		ListComicCommentRequest request = new ListComicCommentRequest();
		request.setComicId(1L);
		PageResponse<WorksCommentDto> response = commentService.listComicComment(request, new Comic());
		assertThat(response.getList().size()).isEqualTo(3);
		assertThat(response.getList().get(0).getCommentId()).isEqualTo(11L);
	}

	@Test
	public void listComicCommentFailedWhenComicNotExist() {
		ListComicCommentRequest request = new ListComicCommentRequest();
		request.setComicId(-1L);
		assertException(InvalidArgumentException.class, () ->commentService.listComicComment(request, new Comic()));
	}
}
