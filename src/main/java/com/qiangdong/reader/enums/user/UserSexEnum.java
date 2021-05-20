package com.qiangdong.reader.enums.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum UserSexEnum implements BaseEnum {

    WOMAN(0, "女"),
    MAN(1, "男"),
    NONE(2, "未知");

    @EnumValue
    @JsonValue
    private int code;
    private String description;

    UserSexEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int value() {
        return code;
    }

    @Override
    public String toString() {

        return "UserSexEnum{" +
                "code=" + code + ", description='" + description + '\'' +
                '}';
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
