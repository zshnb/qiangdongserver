package com.qiangdong.reader.request.topic;

import com.qiangdong.reader.request.BaseRequest;

public class SearchTopicRequest extends BaseRequest {
    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
