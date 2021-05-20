package com.qiangdong.reader.enums.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum TransactionWayEnum implements BaseEnum {
    NONE(0, ""),
    WECHAT(1, "微信"),
    ALIPAY(2, "支付宝"),
    ;
    @EnumValue
    @JsonValue
    private int code;
    private String description;

    TransactionWayEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String description() {
        return description;
    }
}
