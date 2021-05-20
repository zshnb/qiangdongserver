package com.qiangdong.reader.request.type;

import com.qiangdong.reader.enums.type.TypeBelongEnum;
import com.qiangdong.reader.request.BaseRequest;


public class ListTypeRequest extends BaseRequest {
    private TypeBelongEnum belong = TypeBelongEnum.NONE;
    private Long parentId = 0L;

    public TypeBelongEnum getBelong() {
        return belong;
    }

    public void setBelong(TypeBelongEnum belong) {
        this.belong = belong;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
