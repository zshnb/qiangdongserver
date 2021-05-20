package com.qiangdong.reader.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qiangdong.reader.config.JsonLongSerializer;
import com.qiangdong.reader.enums.swiper.LinkTypeEnum;
import com.qiangdong.reader.enums.type.TypeBelongEnum;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 轮播图
 * </p>
 *
 * @author qiangdong app
 * @since 2020-06-11
 */
public class Swiper implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    /**
     * 轮播项目id
     */
    private Long itemId;

    /**
     * 封面
     */
    private String cover;

    private String link;

    private LinkTypeEnum linkType;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    @TableField(update = "now()")
    private LocalDateTime updateAt;

    private LocalDateTime expireAt;

    private TypeBelongEnum type;

    @TableField("`index`")
    private Integer index;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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
        return "Swiper{" +
            "id=" + id +
            ", itemId=" + itemId +
            ", cover=" + cover +
            ", createAt=" + createAt +
            ", updateAt=" + updateAt +
        "}";
    }

    public TypeBelongEnum getType() {
        return type;
    }

    public void setType(TypeBelongEnum type) {
        this.type = type;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }

    public LinkTypeEnum getLinkType() {
        return linkType;
    }

    public void setLinkType(LinkTypeEnum linkType) {
        this.linkType = linkType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
