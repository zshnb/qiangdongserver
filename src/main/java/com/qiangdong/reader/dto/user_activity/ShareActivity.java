package com.qiangdong.reader.dto.user_activity;

import java.util.ArrayList;
import java.util.List;

public class ShareActivity {
    private Long referActivityId = 0L;
    private int agreeCount = 0;
    private int commentCount = 0;
    private int againstCount = 0;
    private String content = "";
    private List<String> images = new ArrayList<>();

    public Long getReferActivityId() {
        return referActivityId;
    }

    public void setReferActivityId(Long referActivityId) {
        this.referActivityId = referActivityId;
    }

    public int getAgreeCount() {
        return agreeCount;
    }

    public void setAgreeCount(int agreeCount) {
        this.agreeCount = agreeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getAgainstCount() {
        return againstCount;
    }

    public void setAgainstCount(int againstCount) {
        this.againstCount = againstCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
