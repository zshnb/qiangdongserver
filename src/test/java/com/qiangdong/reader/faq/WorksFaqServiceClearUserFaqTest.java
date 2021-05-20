package com.qiangdong.reader.faq;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.FaqDto;
import com.qiangdong.reader.request.faq.ListFaqRequest;
import com.qiangdong.reader.serviceImpl.WorksFaqServiceImpl;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorksFaqServiceClearUserFaqTest extends BaseTest {

	@Autowired
	private WorksFaqServiceImpl faqService;

	@Test
	public void clearUserFaqSuccessful(){
		ListFaqRequest request = new ListFaqRequest();
		request.setUserId(1L);
		faqService.clearUserFaq(request);
		List<FaqDto> data = faqService.listFaq(request).getList();
		assertThat(data.size()).isEqualTo(0);
	}
}
