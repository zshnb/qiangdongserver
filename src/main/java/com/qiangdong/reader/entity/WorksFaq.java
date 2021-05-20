package com.qiangdong.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qiangdong.reader.enums.common.CommonReadStatusEnum;
import com.qiangdong.reader.enums.common.WorksTypeEnum;
import com.qiangdong.reader.enums.faq.FaqTypeEnum;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-23
 */
public class WorksFaq implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 问题分类
     */
    private FaqTypeEnum faqType;

    /**
     * 创作者
     */
    private Long userId;

    /**
     * 作品ID
     */
    private Long worksId;

    /**
     * 作品类型
     */
    private WorksTypeEnum worksType;

    /**
     * 问题
     */
    private String question;

    /**
     * 回复答案
     */
    private String answer;

    /**
     * 已读状态
     */
    private CommonReadStatusEnum readStatus;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FaqTypeEnum getFaqType() {
        return faqType;
    }

    public void setFaqType(FaqTypeEnum faqType) {
        this.faqType = faqType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }

    public WorksTypeEnum getWorksType() {
        return worksType;
    }

    public void setWorksType(WorksTypeEnum worksType) {
        this.worksType = worksType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public CommonReadStatusEnum getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(CommonReadStatusEnum readStatus) {
        this.readStatus = readStatus;
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

    @Override
    public String toString() {
        return "WorksFaq{" +
            "id=" + id +
            ", faqType=" + faqType +
            ", userId=" + userId +
            ", worksId=" + worksId +
            ", worksType=" + worksType +
            ", question=" + question +
            ", answer=" + answer +
            ", readStatus=" + readStatus +
            ", createAt=" + createAt +
            ", updateAt=" + updateAt +
        "}";
    }
}
