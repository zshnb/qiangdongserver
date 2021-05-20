package com.qiangdong.reader.enums.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;


public enum VerifyWayEnum implements BaseEnum {
    NONE(0, ""),
    MOBILE(1, "手机"),
    EMAIL(2, "邮箱");

    @EnumValue
    @JsonValue
    private int code;
    private String description;

    VerifyWayEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String toString() {
        return "VerifyWayEnum{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }


}
