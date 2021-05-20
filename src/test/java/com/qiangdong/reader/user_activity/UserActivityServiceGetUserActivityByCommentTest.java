package com.qiangdong.reader.user_activity;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.user_activity.UserActivityDto;
import com.qiangdong.reader.entity.Comment;
import com.qiangdong.reader.enums.comment.CommentTypeEnum;
import com.qiangdong.reader.request.comment.PublishUserActivityCommentRequest;
import com.qiangdong.reader.request.comment.ReplyCommentRequest;
import com.qiangdong.reader.request.user_activity.GetUserActivityByCommentRequest;
import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
import com.qiangdong.reader.serviceImpl.UserActivityServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserActivityServiceGetUserActivityByCommentTest extends BaseTest {
    @Autowired
    private UserActivityServiceImpl userActivityService;

    @Autowired
    private CommentServiceImpl commentService;

    @Test
    public void getUserActivityByCommentSuccessful() {
        PublishUserActivityCommentRequest publishUserActivityCommentRequest = new PublishUserActivityCommentRequest();
        publishUserActivityCommentRequest.setUserId(1L);
        publishUserActivityCommentRequest.setUserActivityId(1L);
        publishUserActivityCommentRequest.setContent("content");
        Comment comment = commentService.publishUserActivityComment(publishUserActivityCommentRequest).getData();

        GetUserActivityByCommentRequest request = new GetUserActivityByCommentRequest();
        request.setCommentId(comment.getId());
        UserActivityDto userActivityDto = userActivityService.getUserActivityByComment(request, new Comment()).getData();
        assertThat(userActivityDto.getId()).isEqualTo(1L);
    }

    @Test
    public void getUserActivityByReplySuccessful() {
        PublishUserActivityCommentRequest publishUserActivityCommentRequest = new PublishUserActivityCommentRequest();
        publishUserActivityCommentRequest.setUserId(1L);
        publishUserActivityCommentRequest.setUserActivityId(1L);
        publishUserActivityCommentRequest.setContent("content");
        Comment comment = commentService.publishUserActivityComment(publishUserActivityCommentRequest).getData();
        ReplyCommentRequest replyCommentRequest = new ReplyCommentRequest();
        replyCommentRequest.setCommentId(comment.getId());
        replyCommentRequest.setType(CommentTypeEnum.USER_ACTIVITY);
        replyCommentRequest.setContent("content");
        Comment reply = commentService.replyComment(replyCommentRequest).getData();

        GetUserActivityByCommentRequest request = new GetUserActivityByCommentRequest();
        request.setCommentId(reply.getId());
        UserActivityDto userActivityDto = userActivityService.getUserActivityByComment(request, new Comment()).getData();
        assertThat(userActivityDto.getId()).isEqualTo(1L);
    }
}
