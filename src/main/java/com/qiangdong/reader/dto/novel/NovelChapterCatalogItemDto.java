package com.qiangdong.reader.dto.novel;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qiangdong.reader.enums.common.WorksChapterReviewStatusEnum;

public class NovelChapterCatalogItemDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id = 0L;
    private String title = "";
    private WorksChapterReviewStatusEnum reviewStatus = WorksChapterReviewStatusEnum.NONE;
    private Integer index = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public WorksChapterReviewStatusEnum getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(WorksChapterReviewStatusEnum reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

}
