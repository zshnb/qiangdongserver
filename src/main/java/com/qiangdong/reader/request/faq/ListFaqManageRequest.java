package com.qiangdong.reader.request.faq;

import com.qiangdong.reader.request.BaseRequest;

public class ListFaqManageRequest extends BaseRequest {
    private Long typeId = 0L;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
