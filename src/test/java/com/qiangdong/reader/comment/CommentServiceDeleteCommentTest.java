package com.qiangdong.reader.comment;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.comment.DeleteCommentRequest;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServiceDeleteCommentTest extends BaseTest {
    @Autowired
    private CommentServiceImpl commentService;

    @Test
    public void deleteCommentSuccessful() {
        DeleteCommentRequest request = new DeleteCommentRequest();
        request.setUserId(authorUserId);
        request.setCommentId(1L);
        commentService.deleteComment(request, new Comment());

        List<Comment> comments = commentService.list();
        assertThat(comments.size()).isEqualTo(11);
    }

    @Test
    public void deleteCommentFailedWhenNotExist() {
        DeleteCommentRequest request = new DeleteCommentRequest();
        request.setUserId(authorUserId);
        request.setCommentId(-1L);
        assertException(InvalidArgumentException.class, () -> commentService.deleteComment(request, new Comment()));
    }

    @Test
    public void deleteCommentFailedWhenPermissionDeny() {
        DeleteCommentRequest request = new DeleteCommentRequest();
        request.setUserId(userId);
        request.setCommentId(1L);
        assertException(PermissionDenyException.class, () -> commentService.deleteComment(request, new Comment()));
    }
}
