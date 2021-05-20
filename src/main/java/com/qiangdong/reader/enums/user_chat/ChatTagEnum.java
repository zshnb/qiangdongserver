package com.qiangdong.reader.enums.user_chat;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum ChatTagEnum implements BaseEnum {
    NONE(0, ""),
    FATEBOARDTAG(1, "三生墙匹配")
    ;

    @JsonValue
    @EnumValue
    private int code;
    private String description;

    ChatTagEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }
}
