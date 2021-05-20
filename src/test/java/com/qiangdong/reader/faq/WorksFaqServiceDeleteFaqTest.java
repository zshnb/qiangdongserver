package com.qiangdong.reader.faq;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.FaqDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.faq.DeleteFaqRequest;
import com.qiangdong.reader.request.faq.ListFaqRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.WorksFaqServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorksFaqServiceDeleteFaqTest extends BaseTest {

	@Autowired
	private WorksFaqServiceImpl faqService;

	@Test
	public void deleteFaqSuccessful() {
		DeleteFaqRequest request = new DeleteFaqRequest();
		request.setFaqId(1L);
		faqService.deleteFaq(request);

		ListFaqRequest request1 = new ListFaqRequest();
		request1.setUserId(1L);
		PageResponse<FaqDto> response = faqService.listFaq(request1);
		assertThat(response.getList().size()).isEqualTo(2);
	}

	@Test
	public void deleteFaqWhenFaqNoExist() {
		DeleteFaqRequest request = new DeleteFaqRequest();
		request.setFaqId(100L);
		assertException(InvalidArgumentException.class, () -> {
			faqService.deleteFaq(request);
		});
	}

}
