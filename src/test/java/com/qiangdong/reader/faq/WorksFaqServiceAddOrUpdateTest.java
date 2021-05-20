package com.qiangdong.reader.faq;


import com.qiangdong.reader.BaseTest;
import com.qiangdong.reader.entity.UserApply;
import com.qiangdong.reader.entity.WorksFaq;
import com.qiangdong.reader.enums.common.CommonReadStatusEnum;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.enums.faq.FaqTypeEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.faq.AddOrUpdateFaqRequest;
import com.qiangdong.reader.serviceImpl.WorksFaqServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WorksFaqServiceAddOrUpdateTest extends BaseTest {

    @Autowired
    private WorksFaqServiceImpl faqService;

    @Test
    public void addFaqSuccessful(){
        AddOrUpdateFaqRequest request = new AddOrUpdateFaqRequest();
        request.setQuestion("Hi");
        request.setFaqType(FaqTypeEnum.REVIEW_QUESTION);
        request.setUserId(1L);
        request.setWorksId(1L);
        request.setWorksType(WorksTypeEnum.NOVEL);
        WorksFaq faq = faqService.addOrUpdateFaq(request).getData();
        assertThat(faq.getId()).isNotZero();
    }

    @Test
    public void updateFaqSuccessful(){
        AddOrUpdateFaqRequest request = new AddOrUpdateFaqRequest();
        request.setFaqId(1L);
        request.setReadStatus(CommonReadStatusEnum.READ);
        faqService.addOrUpdateFaq(request);
        WorksFaq faq = faqService.getById(1L);
        System.err.println(faq);
        assertThat(faq.getReadStatus()).isEqualTo(CommonReadStatusEnum.READ);
    }

    @Test
    public void addFaqWhenUserNoExist(){
        AddOrUpdateFaqRequest request = new AddOrUpdateFaqRequest();
        request.setQuestion("Hi");
        request.setFaqType(FaqTypeEnum.REVIEW_QUESTION);
        request.setUserId(100L);
        request.setWorksId(1L);
        request.setWorksType(WorksTypeEnum.NOVEL);
        assertException(InvalidArgumentException.class, () -> {
            faqService.addOrUpdateFaq(request);
        });
    }


    @Test
    public void addFaqWhenComicNoExist(){
        AddOrUpdateFaqRequest request = new AddOrUpdateFaqRequest();
        request.setQuestion("Hi");
        request.setFaqType(FaqTypeEnum.REVIEW_QUESTION);
        request.setUserId(1L);
        request.setWorksId(10L);
        request.setWorksType(WorksTypeEnum.NOVEL);
        assertException(InvalidArgumentException.class, () -> {
            faqService.addOrUpdateFaq(request);
        });
    }

    @Test
    public void addFaqWhenNovelNoExist(){
        AddOrUpdateFaqRequest request = new AddOrUpdateFaqRequest();
        request.setQuestion("Hi");
        request.setFaqType(FaqTypeEnum.REVIEW_QUESTION);
        request.setUserId(1L);
        request.setWorksId(10L);
        request.setWorksType(WorksTypeEnum.COMIC);
        assertException(InvalidArgumentException.class, () -> {
            faqService.addOrUpdateFaq(request);
        });
    }



}
