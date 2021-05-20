package com.qiangdong.reader.enums.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum TopEnum implements BaseEnum {
    DEFAULT(0, "默认不置顶"),
    TOP(1, "置顶")
    ;
    @EnumValue
    @JsonValue
    private int code;
    private String description;

    TopEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String description() {
        return description;
    }
}
