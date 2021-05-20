//package com.qiangdong.reader.comment;
//
//import com.qiangdong.reader.BaseTest;
//import com.qiangdong.reader.dto.MessageDto;
//import com.qiangdong.reader.dto.user_activity.UserActivityDto;
//import com.qiangdong.reader.entity.Comment;
//import com.qiangdong.reader.enums.message.MessageTypeEnum;
//import com.qiangdong.reader.enums.user_activity.AgreeActivityTypeEnum;
//import com.qiangdong.reader.exception.InvalidArgumentException;
//import com.qiangdong.reader.request.comment.AgreeCommentRequest;
//import com.qiangdong.reader.request.message.ListMessageByTypeRequest;
//import com.qiangdong.reader.serviceImpl.CommentServiceImpl;
//import com.qiangdong.reader.serviceImpl.MessageServiceImpl;
//import java.util.List;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class CommentServiceAgreeCommentTest extends BaseTest {
//	@Autowired
//	private CommentServiceImpl commentService;
//
//	@Autowired
//	private CommentSchedule commentSchedule;
//
//	@Autowired
//	private MessageServiceImpl messageService;
//
//	@Test
//	public void agreeCommentSuccessful() {
//		AgreeCommentRequest request = new AgreeCommentRequest();
//		request.setUserId(2L);
//		request.setCommentId(1L);
//		commentService.agreeComment(request, new Comment());
//		commentSchedule.synchronizeAgreeAndAgainst();
//
//		Comment comment = commentService.getById(1L);
//		assertThat(comment.getAgreeCount()).isEqualTo(2);
//
//		ListMessageByTypeRequest listMessageByTypeRequest = new ListMessageByTypeRequest();
//		listMessageByTypeRequest.setUserId(1L);
//		listMessageByTypeRequest.setMessageType(MessageTypeEnum.AGREE);
//		List<MessageDto> messageDtos = messageService.listMessageByType(listMessageByTypeRequest).getList();
//		assertThat(messageDtos.size()).isEqualTo(1);
//		UserActivityDto activityDto = (UserActivityDto) messageDtos.get(0).getMessage();
//		assertThat(activityDto.getActivityData().getAgreeActivity().getType()).isEqualTo(AgreeActivityTypeEnum.COMMENT);
//	}
//
//	@Test
//	public void agreeCommentFailedWhenNotExist() {
//		AgreeCommentRequest request = new AgreeCommentRequest();
//		request.setCommentId(-1L);
//		assertException(InvalidArgumentException.class, () -> commentService.agreeComment(request, new Comment()));
//	}
//
//	@Test
//	public void agreeCommentFailedWhenTwice() {
//		AgreeCommentRequest request = new AgreeCommentRequest();
//		request.setCommentId(1L);
//		commentService.agreeComment(request, new Comment());
//		assertException(InvalidArgumentException.class, () -> commentService.agreeComment(request, new Comment()));
//	}
//}
