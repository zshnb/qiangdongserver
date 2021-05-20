package com.qiangdong.reader.help_feedback;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.HelpFeedback;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.HelpFeedbackServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HelpFeedbackServiceListHelpFeedbackByUserTest extends BaseTest {

	@Autowired
	private HelpFeedbackServiceImpl helpFeedbackService;

	@Test
	public void listHelpFeedbackSuccessful() {
		BaseRequest request = new BaseRequest();
		request.setUserId(userId);
		PageResponse<HelpFeedback> pageResponse = helpFeedbackService.listHelpFeedbackByUser(request);
		assertThat(pageResponse.getList().size()).isEqualTo(1);
	}
}
