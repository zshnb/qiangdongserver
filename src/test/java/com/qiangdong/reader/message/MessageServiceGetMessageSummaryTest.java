package com.qiangdong.reader.message;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.response.message.GetMessageSummaryResponse;
import com.qiangdong.reader.serviceImpl.MessageServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageServiceGetMessageSummaryTest extends BaseTest {
	@Autowired
	private MessageServiceImpl messageService;

	@Test
	public void getMessageSummarySuccessful() {
		BaseRequest request = new BaseRequest();
		request.setUserId(2L);
		GetMessageSummaryResponse response = messageService.getMessageSummary(request);
		assertThat(response.getFollowMessageCount()).isEqualTo(1);
		assertThat(response.getMentionMessageCount()).isEqualTo(1);
		assertThat(response.getSystemMessageCount()).isEqualTo(1);
		assertThat(response.getAgreeActivityMessageCount()).isEqualTo(1);
	}
}
