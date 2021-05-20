package com.qiangdong.reader.enums.user_chat;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum MessageTypeEnum implements BaseEnum {
    NONE(0, ""),
    HEART(1, "心跳"),
    ;

    @JsonValue
    @EnumValue
    private int code;
    private String description;

    MessageTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }
}
