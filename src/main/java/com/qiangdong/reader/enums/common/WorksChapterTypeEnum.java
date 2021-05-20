package com.qiangdong.reader.enums.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum WorksChapterTypeEnum implements BaseEnum {
    NONE(0, ""),
    PUBLIC(1, "公众"),
    VIP(2, "vip");

    @EnumValue
    @JsonValue
    private int code;
    private String description;

    WorksChapterTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String toString() {
        return "NovelChapterTypeEnum{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int code() {
        return code;
    }
}
