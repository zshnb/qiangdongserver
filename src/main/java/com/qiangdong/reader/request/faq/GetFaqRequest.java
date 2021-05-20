package com.qiangdong.reader.request.faq;

import com.qiangdong.reader.request.BaseRequest;

public class GetFaqRequest extends BaseRequest {

    private Long faqId = 0L;

    public Long getFaqId() {
        return faqId;
    }

    public void setFaqId(Long faqId) {
        this.faqId = faqId;
    }
}
