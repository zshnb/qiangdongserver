package com.qiangdong.reader.help_feedback;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.HelpFeedback;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.HelpFeedbackServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HelpFeedbackServiceListHelpFeedbackTest extends BaseTest {

	@Autowired
	private HelpFeedbackServiceImpl helpFeedbackService;

	@Test
	public void listHelpFeedbackSuccessful() {
		PageResponse<HelpFeedback> pageResponse = helpFeedbackService.listHelpFeedback(new BaseRequest());
		assertThat(pageResponse.getList().size()).isEqualTo(2);
	}
}
