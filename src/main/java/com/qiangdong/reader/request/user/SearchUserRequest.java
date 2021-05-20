package com.qiangdong.reader.request.user;

import com.qiangdong.reader.request.BaseRequest;

public class SearchUserRequest extends BaseRequest {
    private String username = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
