package com.qiangdong.reader.enums.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum WorksTypeEnum implements BaseEnum {
    NONE(0, ""),
    COMIC(1, "漫画"),
    NOVEL(2, "小说");

    @EnumValue
    @JsonValue
    private int code;
    private String description;

    WorksTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String toString() {
        return "CommentTypeEnum{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }

}
