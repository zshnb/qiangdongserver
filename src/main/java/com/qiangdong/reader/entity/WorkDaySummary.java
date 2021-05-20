package com.qiangdong.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.qiangdong.reader.enums.user.AuthorWorkStatusEnum;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author qiangdong app
 * @since 2020-07-05
 */
public class WorkDaySummary implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创作者
     */
    private Long userId;

    private Integer wordCount;

    private AuthorWorkStatusEnum workStatus;

    private LocalDateTime createAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }
    public AuthorWorkStatusEnum getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(AuthorWorkStatusEnum workStatus) {
        this.workStatus = workStatus;
    }
    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "WorkDaySummary{" +
            "id=" + id +
            ", userId=" + userId +
            ", wordCount=" + wordCount +
            ", workStatus=" + workStatus +
            ", createAt=" + createAt +
        "}";
    }
}
