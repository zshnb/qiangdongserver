package com.qiangdong.reader.enums.app_info;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum UpdateTypeEnum implements BaseEnum {
    NONE(0, ""),
    UPDATE_RESOURCE(1, "资源更新"),
    UPDATE_APP(2, "APP更新")
    ;

    @JsonValue
    @EnumValue
    private int code;
    private String description;

    UpdateTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }
}
