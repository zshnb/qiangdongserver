package com.qiangdong.reader.enums.app_info;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum AppTypeEnum implements BaseEnum {
    NONE(0, ""),
    TEST(1, "测试版"),
    FORMAL(2, "正式版")
    ;

    @JsonValue
    @EnumValue
    private int code;
    private String description;

    AppTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }
}
