package com.qiangdong.reader.request.comic;

import com.qiangdong.reader.enums.common.WorksChapterReviewStatusEnum;
import com.qiangdong.reader.enums.common.WorksChapterTypeEnum;
import com.qiangdong.reader.request.BaseRequest;

public class AddOrUpdateComicChapterRequest extends BaseRequest {
    private Long comicId = 0L;

    private Long chapterId = 0L;

    private String title = "";

    private String pictureUrl = "";

    private Double progress = 0.0;

    private WorksChapterTypeEnum type = WorksChapterTypeEnum.NONE;

    private Integer pictureCount = 0;

    private String authorWords = "";

    public Long getComicId() {
        return comicId;
    }

    public void setComicId(Long comicId) {
        this.comicId = comicId;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
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

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public WorksChapterTypeEnum getType() {
        return type;
    }

    public void setType(WorksChapterTypeEnum type) {
        this.type = type;
    }

    public Integer getPictureCount() {
        return pictureCount;
    }

    public void setPictureCount(Integer pictureCount) {
        this.pictureCount = pictureCount;
    }

    public String getAuthorWords() {
        return authorWords;
    }

    public void setAuthorWords(String authorWords) {
        this.authorWords = authorWords;
    }
}
