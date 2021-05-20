package com.qiangdong.reader.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qiangdong.reader.enums.apply.ApplyTypeEnum;
import com.qiangdong.reader.enums.apply.ApplyStatusEnum;
import com.qiangdong.reader.enums.common.WorksTypeEnum;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-16
 */
public class UserApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 请求人ID
     */
    private Long userId;

    /**
     * 请求类型
     */
    private ApplyTypeEnum applyType;

    /**
     * 请求理由
     */
    private String applyReason;

    /**
     * 请求状态
     */
    private ApplyStatusEnum applyStatus;

    private Long worksId;

    private WorksTypeEnum worksType;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    @TableField(update = "now()")
    private LocalDateTime updateAt;

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

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public ApplyTypeEnum getApplyType() {
        return applyType;
    }

    public void setApplyType(ApplyTypeEnum applyType) {
        this.applyType = applyType;
    }

    public ApplyStatusEnum getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(ApplyStatusEnum applyStatus) {
        this.applyStatus = applyStatus;
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
        return "UserApply{" +
                "id=" + id +
                ", applyUser=" + userId +
                ", applyType=" + applyType +
                ", applyText=" + applyReason +
                ", updateType=" + applyStatus +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                "}";
    }
}
