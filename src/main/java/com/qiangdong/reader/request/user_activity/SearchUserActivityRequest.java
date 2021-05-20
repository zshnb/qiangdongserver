package com.qiangdong.reader.request.user_activity;

import com.qiangdong.reader.request.BaseRequest;

public class SearchUserActivityRequest extends BaseRequest {
    private String keyword = "";

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
