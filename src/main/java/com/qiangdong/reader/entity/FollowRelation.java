package com.qiangdong.reader.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qiangdong.reader.config.JsonLongSerializer;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 关注表
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-25
 */
public class FollowRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 关注者id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long followerId;

    /**
     * 被关注用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long followedId;

    /**
     * 是否互相关注
     */
    private Boolean followEach;

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
    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long followerId) {
        this.followerId = followerId;
    }
    public Long getFollowedId() {
        return followedId;
    }

    public void setFollowedId(Long followedId) {
        this.followedId = followedId;
    }
    public Boolean getFollowEach() {
        return followEach;
    }

    public void setFollowEach(Boolean followEach) {
        this.followEach = followEach;
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
        return "FollowRelation{" +
            "id=" + id +
            ", followerId=" + followerId +
            ", followedId=" + followedId +
            ", followEach=" + followEach +
            ", createAt=" + createAt +
            ", updateAt=" + updateAt +
        "}";
    }
}
