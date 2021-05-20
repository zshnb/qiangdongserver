package com.qiangdong.reader.enums.faq;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum FaqTypeEnum implements BaseEnum {
    NONE(0,""),
    WORKS_QUESTION(1, "作品日常管理问题"),
    REVIEW_QUESTION(2, "新建作品审核问题"),
    VIOLATION_QUESTION(3, "作品违规屏蔽问题"),
    SYSTEM_MESSAGE(4, "系统信息");

    @EnumValue
    @JsonValue
    private int code;
    private String description;

    FaqTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String toString() {
        return "FaqTypeEnum{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }
}
