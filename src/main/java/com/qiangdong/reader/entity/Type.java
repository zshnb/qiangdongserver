package com.qiangdong.reader.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qiangdong.reader.enums.type.TypeBelongEnum;

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
@TableName("`type`")
public class Type implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 分类名
     */
    private String name;

    /**
     * 父分类id
     */
    private Long parentId;

    /**
     * 分类所属（小说还是漫画）
     */
    private TypeBelongEnum belong;

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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public TypeBelongEnum getBelong() {
        return belong;
    }

    public void setBelong(TypeBelongEnum belong) {
        this.belong = belong;
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
        return "Type{" +
            "id=" + id +
            ", name=" + name +
            ", parentId=" + parentId +
            ", belong=" + belong +
            ", createAt=" + createAt +
            ", updateAt=" + updateAt +
        "}";
    }
}
