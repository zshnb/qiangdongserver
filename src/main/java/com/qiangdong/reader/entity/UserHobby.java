package com.qiangdong.reader.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qiangdong.reader.config.JsonLongSerializer;
import com.qiangdong.reader.enums.user_hobby.UserHobbyTypeEnum;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author qiangdong app
 * @since 2020-08-17
 */
public class UserHobby implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    @JsonSerialize(using = JsonLongSerializer.class)
    private Long userId;

    /**
     * 兴趣爱好
     */
    private String name;

    /**
     * 兴趣爱好分类
     */
    private UserHobbyTypeEnum type;

    @TableField(fill = FieldFill.INSERT)
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserHobbyTypeEnum getType() {
        return type;
    }

    public void setType(UserHobbyTypeEnum type) {
        this.type = type;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "UserHobby{" +
            "id=" + id +
            ", userId=" + userId +
            ", name=" + name +
            ", type=" + type +
            ", createAt=" + createAt +
        "}";
    }
}
