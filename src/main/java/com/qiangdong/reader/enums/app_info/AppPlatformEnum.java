package com.qiangdong.reader.enums.app_info;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum  AppPlatformEnum implements BaseEnum {
    NONE(0, ""),
    IOS(1, "苹果"),
    ANDROID(2, "安卓")
    ;

    @JsonValue
    @EnumValue
    private int code;
    private String description;

    AppPlatformEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }
}
