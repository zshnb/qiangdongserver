package com.qiangdong.reader.request.faq;

import com.qiangdong.reader.request.BaseRequest;

import java.time.LocalDateTime;

public class ListFaqRequest extends BaseRequest {

    private LocalDateTime createAt = LocalDateTime.now();

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
