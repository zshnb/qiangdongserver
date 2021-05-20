package com.qiangdong.reader.faq;

import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.FaqDto;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.serviceImpl.WorksFaqServiceImpl;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorksFaqServiceListSystemFaqTest extends BaseTest {

	@Autowired
	private WorksFaqServiceImpl faqService;

	@Test
	public void listSystemFaqSuccessful(){
		BaseRequest request = new BaseRequest();
		request.setUserId(1L);
		List<FaqDto> data = faqService.getSystemFaq(request).getList();
		assertThat(data.size()).isEqualTo(1);
		assertThat(data.get(0).getAnswer()).isEqualTo("Hi,BOY");
	}
}
