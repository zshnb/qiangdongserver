package com.qiangdong.reader.enums.user;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum UserChatStatusEnum  implements BaseEnum {
    NONE(0, ""),
    ONLINE(1, "在线"),
    OFFLINE(2, "离线")
    ;

    @EnumValue
    @JsonValue
    private int code;
    private String description;

    UserChatStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }
}
