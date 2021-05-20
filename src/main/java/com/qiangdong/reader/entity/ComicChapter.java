package com.qiangdong.reader.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qiangdong.reader.config.JsonLongSerializer;
import com.qiangdong.reader.enums.common.WorksChapterReviewStatusEnum;
import com.qiangdong.reader.enums.common.WorksChapterTypeEnum;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
public class ComicChapter implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    /**
     * 漫画
     */
    private Long comicId;

    /**
     * 章节名
     */
    private String title;

    /**
     * 进度
     */
    private Double progress;

    /**
     * 图片url
     */
    private String pictureUrl;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    @TableField(update = "now()")
    private LocalDateTime updateAt;

    private Integer deleted;

    /**
     * 章节序号
     */
    @TableField("`index`")
    private Integer index;

    /**
     * 类型(公众| vip)
     */
    @TableField("`type`")
    private WorksChapterTypeEnum type;

    private WorksChapterReviewStatusEnum reviewStatus;

    private Double price;

    private Integer pictureCount;

    private String authorWords;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getComicId() {
        return comicId;
    }

    public void setComicId(Long comicId) {
        this.comicId = comicId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
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
    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
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

    @Override
    public String toString() {
        return "ComicsChapter{" +
            "id=" + id +
            ", comicsId=" + comicId +
            ", title=" + title +
            ", progress=" + progress +
            ", pictureUrl=" + pictureUrl +
            ", createAt=" + createAt +
            ", updateAt=" + updateAt +
            ", deleted=" + deleted +
            ", index=" + index +
            ", type=" + type +
        "}";
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
