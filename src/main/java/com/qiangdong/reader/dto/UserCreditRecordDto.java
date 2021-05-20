package com.qiangdong.reader.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.qiangdong.reader.enums.common.TransactionWayEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserCreditRecordDto {
    private Long id;
    private String description;
    private BigDecimal price;
    private TransactionWayEnum transactionWay;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createAt;
    private Long userId;
    private String username;
    private Integer coin;


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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }
}
