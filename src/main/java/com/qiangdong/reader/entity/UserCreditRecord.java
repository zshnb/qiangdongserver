package com.qiangdong.reader.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qiangdong.reader.enums.common.TransactionWayEnum;
import com.qiangdong.reader.enums.user_credit_record.UserCreditRecordStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 虚拟货币充值记录
 * </p>
 *
 * @author qiangdong app
 * @since 2020-05-28
 */
public class UserCreditRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 充值说明
     */
    private String description;

    /**
     * 充值金额
     */
    private BigDecimal price;

    /**
     * 虚拟货币-墙币
     */
    private Integer coin;

    /**
     * 付费用户
     */
    private Long userId;

    /**
     * 订单流水
     */
    private Long orderNumber;

    /**
     * 第三方充值
     */
    private TransactionWayEnum transactionWay;

    /**
     * 充值时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    @TableField(update = "now()")
    private LocalDateTime updateAt;

    private UserCreditRecordStatusEnum status;

    /**
     * 逻辑删除
     */
    private Integer deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TransactionWayEnum getTransactionWay() {
        return transactionWay;
    }

    public void setTransactionWay(TransactionWayEnum transactionWay) {
        this.transactionWay = transactionWay;
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

    public UserCreditRecordStatusEnum getStatus() {
        return status;
    }

    public void setStatus(UserCreditRecordStatusEnum status) {
        this.status = status;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return "UserCreditRecord{" +
            "id=" + id +
            ", description=" + description +
            ", price=" + price +
            ", coin=" + coin +
            ", userId=" + userId +
            ", orderNumber=" + orderNumber +
            ", transactionWay=" + transactionWay +
            ", createAt=" + createAt +
            ", updateAt=" + updateAt +
            ", deleted=" + deleted +
        "}";
    }
}
