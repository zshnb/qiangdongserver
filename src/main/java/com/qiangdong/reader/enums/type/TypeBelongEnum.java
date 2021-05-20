package com.qiangdong.reader.enums.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum TypeBelongEnum implements BaseEnum {
    NONE(0, ""),
    COMIC(1, "漫画"),
    NOVEL(2, "小说");

    @EnumValue
    @JsonValue
    private int code;
    private String description;

    TypeBelongEnum(Integer value, String description) {
        this.code = value;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String toString() {
        return "TypeBelongEnum{" +
                "code=" + code +", description='" + description + '\'' +
                '}';
    }

}
