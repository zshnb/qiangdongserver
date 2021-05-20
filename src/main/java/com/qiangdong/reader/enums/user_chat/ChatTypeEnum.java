package com.qiangdong.reader.enums.user_chat;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;


public enum  ChatTypeEnum implements BaseEnum {
    TEXT(0, "普通文字消息"),
    IMAGE(1, "图片消息"),
    FILE(2, "文件信息"),
    VOICE(3, "语音信息"),
    VIDEO(4, "视频信息")
    ;

    @JsonValue
    @EnumValue
    private int code;
    private String description;

    ChatTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }
}
