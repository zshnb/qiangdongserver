package com.qiangdong.reader.enums.user_credit_record;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;


public enum  UserCreditRecordStatusEnum implements BaseEnum {
    PENDING(0, "待处理"),
    SUCCESS(1, "完成"),
    FAILURE(2, "失败");

    @JsonValue
    @EnumValue
    private int code;
    private String description;

    UserCreditRecordStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }
}
