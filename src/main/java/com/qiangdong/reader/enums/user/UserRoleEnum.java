package com.qiangdong.reader.enums.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum UserRoleEnum implements BaseEnum {

    NONE(0, ""),
    USER(1, "普通用户"),
    AUTHOR(2, "作者"),
    EDITOR(3, "编辑"),
    ADMINISTRATOR(4, "管理员");

    @EnumValue
    @JsonValue
    private Integer code;
    private String description;

    UserRoleEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TypeBelongEnum{" +
                "code=" + code + ", description='" + description + '\'' +
                '}';
    }

    public Integer value() {
        return code;
    }

    @Override
    public int code() {
        return code;
    }
}
