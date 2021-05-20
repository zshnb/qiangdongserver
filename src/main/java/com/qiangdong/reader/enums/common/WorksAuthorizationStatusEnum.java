package com.qiangdong.reader.enums.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum WorksAuthorizationStatusEnum implements BaseEnum {
    NONE(0, ""),
    EXCLUSIVE(1, "独家首发"),
    STATION(2, "驻站作品");

    @EnumValue
    @JsonValue
    private int code;
    private String description;

    WorksAuthorizationStatusEnum(int code, String description) {
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
