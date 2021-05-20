package com.qiangdong.reader.enums.user_hobby;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum UserHobbyTypeEnum implements BaseEnum {
    NONE(0, ""),
    LISTENING(1, "在听"),
    DOING(2, "在做"),
    LOVE(3, "热爱"),
    LOOKING(4, "在看"),
    PLAYING(5, "在玩"),
    READING(6, "在读"),
    ;

    @JsonValue
    @EnumValue
    private int code;
    private String description;

    UserHobbyTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }

}
