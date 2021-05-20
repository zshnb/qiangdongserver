package com.qiangdong.reader.dto.comic;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qiangdong.reader.enums.common.WorksChapterReviewStatusEnum;
import com.qiangdong.reader.enums.common.WorksChapterTypeEnum;

import java.time.LocalDateTime;


public class ComicAndFirstChapterWithReviewDto {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id = 0L;
    private Integer index = 0;
    private String title = "";
    private String pictureUrl = "";
    private LocalDateTime createAt = LocalDateTime.now();
    private WorksChapterTypeEnum type = WorksChapterTypeEnum.NONE;
    private WorksChapterReviewStatusEnum reviewStatus = WorksChapterReviewStatusEnum.NONE;
    private String comicName = "";
    private String nickName = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public WorksChapterTypeEnum getType() {
        return type;
    }

    public void setType(WorksChapterTypeEnum type) {
        this.type = type;
    }

    public WorksChapterReviewStatusEnum getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(WorksChapterReviewStatusEnum reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getComicName() {
        return comicName;
    }

    public void setComicName(String comicName) {
        this.comicName = comicName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
