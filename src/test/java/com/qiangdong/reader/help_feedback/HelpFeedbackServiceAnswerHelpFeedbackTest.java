package com.qiangdong.reader.help_feedback;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.HelpFeedback;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.exception.PermissionDenyException;
import com.qiangdong.reader.request.help_feedback.AnswerHelpFeedbackRequest;
import com.qiangdong.reader.request.help_feedback.GetHelpFeedbackRequest;
import com.qiangdong.reader.serviceImpl.HelpFeedbackServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HelpFeedbackServiceAnswerHelpFeedbackTest extends BaseTest {

	@Autowired
	private HelpFeedbackServiceImpl helpFeedbackService;

	@Test
	public void answerHelpFeedbackSuccessful() {
		AnswerHelpFeedbackRequest request = new AnswerHelpFeedbackRequest();
		request.setUserId(adminUserId);
		request.setHelpFeedbackId(1L);
		request.setAnswer("answer");
		helpFeedbackService.answeredHelpFeedback(request, new HelpFeedback());

		HelpFeedback helpFeedback = helpFeedbackService.getById(1L);
		assertThat(helpFeedback.getAnswer()).isEqualTo("answer");
	}

	@Test
	public void answerHelpFeedbackFailedWhenIdIsInvalid() {
		AnswerHelpFeedbackRequest request = new AnswerHelpFeedbackRequest();
		request.setUserId(adminUserId);
		request.setHelpFeedbackId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			helpFeedbackService.answeredHelpFeedback(request, new HelpFeedback());
		});
	}

	@Test
	public void answerHelpFeedbackFailedWhenNoPermission() {
		AnswerHelpFeedbackRequest request = new AnswerHelpFeedbackRequest();
		request.setHelpFeedbackId(1L);
		assertException(PermissionDenyException.class, () -> {
			helpFeedbackService.answeredHelpFeedback(request, new HelpFeedback());
		});
	}
}
