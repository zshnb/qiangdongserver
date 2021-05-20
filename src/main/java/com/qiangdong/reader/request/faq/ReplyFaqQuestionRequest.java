package com.qiangdong.reader.request.faq;

import com.qiangdong.reader.request.BaseRequest;

public class ReplyFaqQuestionRequest extends BaseRequest {
    private Long worksFaqId = 0L;
    private String answer = "";

    public Long getWorksFaqId() {
        return worksFaqId;
    }

    public void setWorksFaqId(Long worksFaqId) {
        this.worksFaqId = worksFaqId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
