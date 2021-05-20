package com.qiangdong.reader.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class UserPreferTypeDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long preferTypeId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long typeId;
    private String name;

    public Long getPreferTypeId() {
        return preferTypeId;
    }

    public void setPreferTypeId(Long preferTypeId) {
        this.preferTypeId = preferTypeId;
    }

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
}
