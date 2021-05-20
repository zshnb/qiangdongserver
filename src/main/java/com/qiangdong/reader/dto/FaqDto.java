package com.qiangdong.reader.dto;


import com.qiangdong.reader.enums.common.CommonReadStatusEnum;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.enums.faq.FaqTypeEnum;

import java.time.LocalDateTime;

public class FaqDto {

    private String faqId;
    private FaqTypeEnum faqType;
    private String worksName;
    private WorksTypeEnum worksType;
    private String question;
    private String answer;
    private CommonReadStatusEnum readStatus;
    private LocalDateTime createAt;
    private String nickname;
    private String avatar;
    private Boolean isReply;

    public String getFaqId() {
        return faqId;
    }

    public void setFaqId(String faqId) {
        this.faqId = faqId;
    }

    public FaqTypeEnum getFaqType() {
        return faqType;
    }

    public void setFaqType(FaqTypeEnum faqType) {
        this.faqType = faqType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getWorksName() {
        return worksName;
    }

    public void setWorksName(String worksName) {
        this.worksName = worksName;
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

    public WorksTypeEnum getWorksType() {
        return worksType;
    }

    public void setWorksType(WorksTypeEnum worksType) {
        this.worksType = worksType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getReply() {
        return isReply;
    }

    public void setReply(Boolean reply) {
        isReply = reply;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
