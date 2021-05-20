package com.qiangdong.reader.enums.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum WorksContractStatusEnum implements BaseEnum {
    NONE(0, ""),
    SIGNED(1, "签约"),
    UNSIGNED(2, "未签约");

    @EnumValue
    @JsonValue
    private int code;
    private String description;

    WorksContractStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String toString() {
        return "NovelContractStatusEnum{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }

}
