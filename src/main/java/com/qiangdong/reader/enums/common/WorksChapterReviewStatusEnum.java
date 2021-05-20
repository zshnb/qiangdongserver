package com.qiangdong.reader.enums.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.qiangdong.reader.enums.BaseEnum;

public enum WorksChapterReviewStatusEnum  implements BaseEnum {

    NONE(0, "无需处理"),
    PENDING(1, "待审核"),
    PASS(2, "通过审核"),
    REJECT(3, "驳回");

    @EnumValue
    @JsonValue
    private int code;
    private String description;

    WorksChapterReviewStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ComicChapterReviewStatusEnum{" +
                "code=" + code +", description='" + description + '\'' +
                '}';
    }

    @Override
    public int code() {
        return code;
    }

}
