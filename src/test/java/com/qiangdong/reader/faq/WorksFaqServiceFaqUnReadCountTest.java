package com.qiangdong.reader.faq;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.serviceImpl.WorksFaqServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorksFaqServiceFaqUnReadCountTest extends BaseTest {

	@Autowired
	private WorksFaqServiceImpl faqService;

	@Test
	public void GetFaqUnReadCountSuccessful() {
		BaseRequest request = new BaseRequest();
		request.setUserId(1L);
		Integer count = faqService.getUnReadFaqCount(request).getData();
		assertThat(count).isEqualTo(2);
	}

}
