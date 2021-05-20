package com.qiangdong.reader.response.user;


public class GetAuthorCompletionResponse {
    Integer authorCompletion;
    Boolean isAuthentication;

    public Integer getAuthorCompletion() {
        return authorCompletion;
    }

    public void setAuthorCompletion(Integer authorCompletion) {
        this.authorCompletion = authorCompletion;
    }

    public Boolean getAuthentication() {
        return isAuthentication;
    }

    public void setAuthentication(Boolean authentication) {
        isAuthentication = authentication;
    }
}
