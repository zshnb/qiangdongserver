package com.qiangdong.reader.help_feedback;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.HelpFeedback;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.help_feedback.DeleteHelpFeedbackRequest;
import com.qiangdong.reader.serviceImpl.HelpFeedbackServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HelpFeedbackServiceDeleteTest extends BaseTest {

	@Autowired
	private HelpFeedbackServiceImpl helpFeedbackService;

	@Test
	public void deleteHelpFeedbackSuccessful() {
		DeleteHelpFeedbackRequest request = new DeleteHelpFeedbackRequest();
		request.setHelpFeedbackId(1L);
		helpFeedbackService.deletedHelpFeedback(request, new HelpFeedback());
		HelpFeedback helpFeedback = helpFeedbackService.getById(request.getHelpFeedbackId());
		assertThat(helpFeedback).isNull();
	}

	@Test
	public void deleteHelpFeedbackFailedIdIsInvalid() {
		DeleteHelpFeedbackRequest request = new DeleteHelpFeedbackRequest();
		request.setHelpFeedbackId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			helpFeedbackService.deletedHelpFeedback(request, new HelpFeedback());
		});
	}
}
