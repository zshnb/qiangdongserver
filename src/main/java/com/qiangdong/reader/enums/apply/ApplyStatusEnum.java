package com.qiangdong.reader.enums.apply;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum ApplyStatusEnum implements BaseEnum {

    NONE(0, ""),
    PENDING(1, "待处理"),
    PASS(2, "通过"),
    DISMISS(3, "驳回");

    @EnumValue
    @JsonValue
    private int code;
    private String description;

    ApplyStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String toString() {
        return "NovelChapterTypeEnum{" +
                "code=" + code + ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int code() {
        return code;
    }
}
