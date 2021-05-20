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
public class NovelChapter implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    /**
     * 小说
     */
    private Long novelId;

    /**
     * 章节名
     */
    private String title;

    /**
     * 字数
     */
    private Integer wordCount;

    /**
     * 章节文本url
     */
    private String txtUrl;

    /**
     * 进度
     */
    private Double progress;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    @TableField(fill = FieldFill.INSERT, update = "now()")
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

    /**
     * 审核状态
     */
    private WorksChapterReviewStatusEnum reviewStatus;

    private Double price;

    private String authorWords;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getNovelId() {
        return novelId;
    }

    public void setNovelId(Long novelId) {
        this.novelId = novelId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }
    public String getTxtUrl() {
        return txtUrl;
    }

    public void setTxtUrl(String txtUrl) {
        this.txtUrl = txtUrl;
    }
    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
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
        return "NovelChapter{" +
            "id=" + id +
            ", novelId=" + novelId +
            ", title=" + title +
            ", wordCount=" + wordCount +
            ", txtUrl=" + txtUrl +
            ", progress=" + progress +
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

    public String getAuthorWords() {
        return authorWords;
    }

    public void setAuthorWords(String authorWords) {
        this.authorWords = authorWords;
    }
}
