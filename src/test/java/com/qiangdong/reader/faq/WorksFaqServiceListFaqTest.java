package com.qiangdong.reader.faq;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.FaqDto;
import com.qiangdong.reader.request.faq.ListFaqRequest;
import com.qiangdong.reader.response.PageResponse;
import com.qiangdong.reader.serviceImpl.WorksFaqServiceImpl;
import org.junit.Test;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;

public class WorksFaqServiceListFaqTest extends BaseTest {

    @Autowired
    private WorksFaqServiceImpl faqService;

    @Test
    public void listFaqSuccessful() {
        ListFaqRequest request = new ListFaqRequest();
        request.setUserId(1L);
        PageResponse<FaqDto> response = faqService.listFaq(request);
        assertThat(response.getList().size()).isEqualTo(3);
    }


    public void listFaqWhenUserNoExist() {
        ListFaqRequest request = new ListFaqRequest();
        request.setUserId(-1L);
        assertException(InvalidPropertyException.class, () -> {
            faqService.listFaq(request);
        });
    }


}
