package com.qiangdong.reader.faq;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.serviceImpl.WorksFaqServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorksFaqServiceReadUserAllFaqTest extends BaseTest {

	@Autowired
	private WorksFaqServiceImpl faqService;

	@Test
	public void ReadUserAllFaqSuccessful(){
		BaseRequest request = new BaseRequest();
		request.setUserId(1L);
	}

}
