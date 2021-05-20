package com.qiangdong.reader.dto;

import java.io.Serializable;

public class MenuDto implements Serializable {

    private Long value;
    private String label;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
