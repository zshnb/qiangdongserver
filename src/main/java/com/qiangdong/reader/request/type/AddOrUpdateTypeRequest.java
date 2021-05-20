package com.qiangdong.reader.request.type;

import com.qiangdong.reader.enums.type.TypeBelongEnum;
import com.qiangdong.reader.request.BaseRequest;


public class AddOrUpdateTypeRequest extends BaseRequest {

    private Long typeId = 0L;
    private String name = "";
    private Long parentId = 0L;
    private TypeBelongEnum belong = TypeBelongEnum.NONE;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public TypeBelongEnum getBelong() {
        return belong;
    }

    public void setBelong(TypeBelongEnum belong) {
        this.belong = belong;
    }


}
