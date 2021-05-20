package com.qiangdong.reader.enums.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum DeviceEnum implements BaseEnum {
    NONE(0, ""),
    IOS(1, "苹果"),
    ANDROID(2, "安卓");

    @EnumValue
    @JsonValue
    private int code;
    private String description;

    DeviceEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String toString() {
        return "DeviceEnum{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }
}
