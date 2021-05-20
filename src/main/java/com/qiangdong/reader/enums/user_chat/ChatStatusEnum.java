package com.qiangdong.reader.enums.user_chat;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum ChatStatusEnum implements BaseEnum {
    NONE(0, ""),
    SUCCESS(1, "成功"),
    BLOCKED(2, "阻塞/被拉黑"),
    OHTHER(3, "其他"),
    ;

    @JsonValue
    @EnumValue
    private int code;
    private String description;

    ChatStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }
}
