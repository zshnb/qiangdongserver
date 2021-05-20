package com.qiangdong.reader.comment;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comment.AgainstCommentRequest;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServiceAgainstCommentTest extends BaseTest {
	@Autowired
	private CommentServiceImpl commentService;

	@Test
	public void againstCommentFailedWhenNotExist() {
		AgainstCommentRequest request = new AgainstCommentRequest();
		request.setCommentId(-1L);
		assertException(InvalidArgumentException.class, () -> commentService.againstComment(request, new Comment()));
	}

	@Test
	public void againstCommentFailedWhenTwice() {
		AgainstCommentRequest request = new AgainstCommentRequest();
		request.setCommentId(1L);
		commentService.againstComment(request, new Comment());
		assertException(InvalidArgumentException.class, () -> commentService.againstComment(request, new Comment()));
	}
}
