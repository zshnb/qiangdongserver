package com.qiangdong.reader.enums.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum WorksShowStatusEnum implements BaseEnum {
    NORMAL(0, "正常"),
    VISIBLE(1, "上架"),
    INVISIBLE(2, "下架");

    @EnumValue
    @JsonValue
    private int code;
    private String description;

    WorksShowStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String toString() {
        return "NovelShowStatusEnum{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }
}
