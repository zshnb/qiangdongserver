package com.qiangdong.reader.enums.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum CommonReadStatusEnum implements BaseEnum {
    NONE(0, ""),
    UNREAD(1, "未读"),
    READ(2, "已读");

    @EnumValue
    @JsonValue
    private int code;
    private String description;

    CommonReadStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String toString() {
        return "CommonReadStatusEnum{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }
}
