package com.qiangdong.reader.faq;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.dto.FaqDto;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.faq.GetFaqRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.WorksFaqServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorksFaqServiceGetFaqTest extends BaseTest {

    @Autowired
    private WorksFaqServiceImpl faqService;

    @Test
    public void getFaqSuccessful() {
        GetFaqRequest request = new GetFaqRequest();
        request.setFaqId(1L);
        Response<FaqDto> response = faqService.getFaq(request);
        assertThat(response.getData().getFaqId()).isEqualTo("1");
        assertThat(response.getData().getWorksName()).isEqualTo("novel1");
        assertThat(response.getData().getWorksType()).isEqualTo(WorksTypeEnum.NOVEL);
    }

    @Test
    public void getFaqWhenFaqNoExist() {
        GetFaqRequest request = new GetFaqRequest();
        request.setFaqId(100L);
        assertException(InvalidArgumentException.class, () -> {
            faqService.getFaq(request);
        });
    }
}