package com.qiangdong.reader.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qiangdong.reader.config.JsonLongSerializer;
import com.qiangdong.reader.dto.user_activity.ActivityData;
import com.qiangdong.reader.enums.user_activity.ActivityTypeEnum;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户动态表
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-25
 */
public class UserActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 动态内容
     */
    private ActivityData activityData;

    @TableField("`type`")
    private ActivityTypeEnum type;

    private Boolean top;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    @TableField(fill = FieldFill.INSERT, update = "now()")
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
    public ActivityData getActivityData() {
        return activityData;
    }

    public void setActivityData(ActivityData activityData) {
        this.activityData = activityData;
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
        return "UserActivity{" +
            "id=" + id +
            ", userId=" + userId +
            ", data=" + activityData +
            ", createAt=" + createAt +
            ", updateAt=" + updateAt +
        "}";
    }

    public ActivityTypeEnum getType() {
        return type;
    }

    public void setType(ActivityTypeEnum type) {
        this.type = type;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }
}
