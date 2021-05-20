package com.qiangdong.reader.help_feedback;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.HelpFeedback;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.serviceImpl.HelpFeedbackServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HelpFeedbackServiceAddOrUpdateHelpFeedbackTest extends BaseTest {

	@Autowired
	private HelpFeedbackServiceImpl helpFeedbackService;

	@Test
	public void addHelpFeedbackSuccessful() {
		HelpFeedback helpFeedback = helpFeedbackService
			.addOrUpdateHelpFeedback(addOrUpdateHelpFeedbackRequest)
			.getData();
		assertThat(helpFeedback.getContent()).isEqualTo("test content");
	}

	@Test
	public void updateHelpFeedbackSuccessful() {
		addOrUpdateHelpFeedbackRequest.setHelpFeedbackId(1L);
		HelpFeedback helpFeedback = helpFeedbackService
			.addOrUpdateHelpFeedback(addOrUpdateHelpFeedbackRequest)
			.getData();
		assertThat(helpFeedback.getContent()).isEqualTo("test content");
	}

	@Test
	public void updateHelpFeedbackFailedWhenIdIsInvalid() {
		addOrUpdateHelpFeedbackRequest.setHelpFeedbackId(100L);
		assertException(InvalidArgumentException.class, () -> {
			helpFeedbackService.addOrUpdateHelpFeedback(addOrUpdateHelpFeedbackRequest);
		});
	}
}
