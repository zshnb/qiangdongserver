package com.qiangdong.reader.enums.apply;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum ApplyTypeEnum implements BaseEnum {
    NONE(0, ""),
    SIGN(1, "签约"),
    RECOMMEND(2, "推荐"),
    FULL_TIME(3, "全勤");

    @EnumValue
    @JsonValue
    private int code;
    private String description;

    ApplyTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String toString() {
        return "CommentTypeEnum{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }

}
