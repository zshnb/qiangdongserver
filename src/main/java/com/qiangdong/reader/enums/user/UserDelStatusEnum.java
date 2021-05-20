package com.qiangdong.reader.enums.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;


public enum UserDelStatusEnum implements BaseEnum {

    NORMAL(0, "正常"),
    DELETE(1, "删除"),
    STOP(2, "停用");

    @EnumValue
    @JsonValue
    private int code;
    private String description;

    UserDelStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int value() {
        return code;
    }

    @Override
    public String toString() {
        return "UserDelStatusEnum{" +
                "code=" + code + ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int code() {
        return code;
    }

}
