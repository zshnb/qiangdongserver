package com.qiangdong.reader.help_feedback;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.HelpFeedback;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.help_feedback.GetHelpFeedbackRequest;
import com.qiangdong.reader.serviceImpl.HelpFeedbackServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HelpFeedbackServiceGetHelpFeedTest extends BaseTest {

	@Autowired
	private HelpFeedbackServiceImpl helpFeedbackService;

	@Test
	public void getHelpFeedbackSuccessful() {
		GetHelpFeedbackRequest request = new GetHelpFeedbackRequest();
		request.setHelpFeedbackId(1L);
		HelpFeedback helpFeedback = helpFeedbackService.getHelpFeedback(request, new HelpFeedback()).getData();
		assertThat(helpFeedback).isNotNull();
		assertThat(helpFeedback.getContent()).isEqualTo("content");
	}

	@Test
	public void getHelpFeedbackFailedWhenIdIsInvalid() {
		GetHelpFeedbackRequest request = new GetHelpFeedbackRequest();
		request.setHelpFeedbackId(-1L);
		assertException(InvalidArgumentException.class, () -> {
			helpFeedbackService.getHelpFeedback(request, new HelpFeedback());
		});
	}
}
