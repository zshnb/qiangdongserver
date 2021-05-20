package com.qiangdong.reader.request.type;

import com.qiangdong.reader.request.BaseRequest;


public class DeleteTypeRequest extends BaseRequest {
    private Long typeId = 0L;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
