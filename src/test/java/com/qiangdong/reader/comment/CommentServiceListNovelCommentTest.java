package com.qiangdong.reader.comment;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.WorksCommentDto;
import com.qiangdong.reader.entity.Novel;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.comment.ListNovelCommentRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServiceListNovelCommentTest extends BaseTest {
	@Autowired
	private CommentServiceImpl commentService;

	@Test
	public void listNovelCommentSuccessful() {
		ListNovelCommentRequest request = new ListNovelCommentRequest();
		request.setNovelId(1L);
		PageResponse<WorksCommentDto> response = commentService.listNovelComment(request, new Novel());
		Assert.assertEquals(1, response.getList().size());
		Assert.assertEquals("user1", response.getList().get(0).getUsername());
		Assert.assertEquals("content", response.getList().get(0).getContent());
	}

	@Test
	public void listNovelCommentFailedWhenNovelNotExist() {
		ListNovelCommentRequest request = new ListNovelCommentRequest();
		request.setNovelId(-1L);
		assertException(InvalidArgumentException.class, () ->commentService.listNovelComment(request, new Novel()));
	}
}
