package com.qiangdong.reader.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qiangdong.reader.config.JsonLongSerializer;
import com.qiangdong.reader.enums.common.WorksAuthorizationStatusEnum;
import com.qiangdong.reader.enums.common.WorksContractStatusEnum;
import com.qiangdong.reader.enums.common.WorksShowStatusEnum;
import com.qiangdong.reader.enums.common.WorksUpdateStatusEnum;
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
public class Comic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long id;

    /**
     * 漫画名称
     */
    private String name;

    /**
     * 封面url
     */
    private String cover;

    /**
     * 作者
     */
    private Long authorId;

    /**
     * 更新状态
     */
    private WorksUpdateStatusEnum updateStatus;

    /**
     * 签约状态
     */
    private WorksContractStatusEnum contractStatus;

    /**
     * 上架状态
     */
    private WorksShowStatusEnum showStatus;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime updateAt;

    private Integer deleted;

    /**
     * 收藏数
     */
    private Integer collectCount;

    /**
     * 推荐数
     */
    private Integer recommendTicket;

    /**
     * 墙票
     */
    private Integer wallTicket;

    /**
     * 墙币
     */
    private Integer coin;

    /**
     * 分类
     */
    private Long typeId;

    /**
     * 点击次数
     */
    private Integer clickCount;

    /**
     * 简介
     */
    private String description;

    private WorksAuthorizationStatusEnum authorizationStatus;

    private Long topicId;

    private Integer pictureCount;

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
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
    public WorksUpdateStatusEnum getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(WorksUpdateStatusEnum updateStatus) {
        this.updateStatus = updateStatus;
    }


    public WorksShowStatusEnum getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(WorksShowStatusEnum showStatus) {
        this.showStatus = showStatus;
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
    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }
    public Integer getRecommendTicket() {
        return recommendTicket;
    }

    public void setRecommendTicket(Integer recommendTicket) {
        this.recommendTicket = recommendTicket;
    }
    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public WorksContractStatusEnum getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(WorksContractStatusEnum contractStatus) {
        this.contractStatus = contractStatus;
    }

    @Override
    public String toString() {
        return "Comics{" +
            "id=" + id +
            ", name=" + name +
            ", cover=" + cover +
            ", authorId=" + authorId +
            ", updateStatus=" + updateStatus +
            ", contractStatus=" + contractStatus +
            ", showStatus=" + showStatus +
            ", createAt=" + createAt +
            ", updateAt=" + updateAt +
            ", deleted=" + deleted +
            ", collectCount=" + collectCount +
            ", recommendCount=" + recommendTicket +
            ", typeId=" + typeId +
            ", clickCount=" + clickCount +
            ", description=" + description +
        "}";
    }

    public Integer getWallTicket() {
        return wallTicket;
    }

    public void setWallTicket(Integer wallTicket) {
        this.wallTicket = wallTicket;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public WorksAuthorizationStatusEnum getAuthorizationStatus() {
        return authorizationStatus;
    }

    public void setAuthorizationStatus(WorksAuthorizationStatusEnum authorizationStatus) {
        this.authorizationStatus = authorizationStatus;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Integer getPictureCount() {
        return pictureCount;
    }

    public void setPictureCount(Integer pictureCount) {
        this.pictureCount = pictureCount;
    }
}
