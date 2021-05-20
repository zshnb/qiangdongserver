package com.qiangdong.reader.request.faq;


import com.qiangdong.reader.enums.common.CommonReadStatusEnum;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.enums.faq.FaqTypeEnum;
import com.qiangdong.reader.request.BaseRequest;


public class AddOrUpdateFaqRequest extends BaseRequest {
    private Long faqId = 0L;
    private FaqTypeEnum faqType = FaqTypeEnum.NONE;
    private Long userId = 0L;
    private Long worksId = 0L;
    private WorksTypeEnum worksType= WorksTypeEnum.NONE;
    private String question = "";
    private String answer = "";
    private CommonReadStatusEnum readStatus = CommonReadStatusEnum.NONE;

    public Long getFaqId() {
        return faqId;
    }

    public void setFaqId(Long faqId) {
        this.faqId = faqId;
    }

    public FaqTypeEnum getFaqType() {
        return faqType;
    }

    public void setFaqType(FaqTypeEnum faqType) {
        this.faqType = faqType;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }

    public WorksTypeEnum getWorksType() {
        return worksType;
    }

    public void setWorksType(WorksTypeEnum worksType) {
        this.worksType = worksType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public CommonReadStatusEnum getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(CommonReadStatusEnum readStatus) {
        this.readStatus = readStatus;
    }
}
