package com.qiangdong.reader.comment;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.comment.ChangeTopRequest;
import com.qiangdong.reader.request.comment.DeleteCommentRequest;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServiceChangeTopTest extends BaseTest {
    @Autowired
    private CommentServiceImpl commentService;

    @Test
    public void changeTopSuccessful() {
        ChangeTopRequest request = new ChangeTopRequest();
        request.setUserId(authorUserId);
        request.setCommentId(1L);
        request.setTop(true);
        commentService.changeTop(request, new Comment());

        Comment comment = commentService.getById(1L);
        assertThat(comment.getTop()).isTrue();
    }

    @Test
    public void changeTopFailedWhenNotExist() {
        DeleteCommentRequest request = new DeleteCommentRequest();
        request.setUserId(authorUserId);
        request.setCommentId(-1L);
        assertException(InvalidArgumentException.class, () -> commentService.deleteComment(request, new Comment()));
    }

    @Test
    public void changeTopFailedWhenPermissionDeny() {
        DeleteCommentRequest request = new DeleteCommentRequest();
        request.setUserId(userId);
        request.setCommentId(1L);
        assertException(PermissionDenyException.class, () -> commentService.deleteComment(request, new Comment()));
    }
}
