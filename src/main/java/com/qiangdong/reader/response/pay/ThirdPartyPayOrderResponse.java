package com.qiangdong.reader.response.pay;

import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.qiangdong.reader.dto.pay.WechatPayDto;
import com.qiangdong.reader.enums.common.TransactionWayEnum;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * APP支付下单 响应参数
 */
public class ThirdPartyPayOrderResponse {
    private String orderNumber;
    private String subject;
    private LocalDateTime orderExpire;
    private TransactionWayEnum transactionWay;
    private String tradeType;
    private String openId;
    private WechatPayDto wechatPayDto;
    private AlipayTradeAppPayResponse aliPayDto;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDateTime getOrderExpire() {
        return orderExpire;
    }

    public void setOrderExpire(LocalDateTime orderExpire) {
        this.orderExpire = orderExpire;
    }

    public TransactionWayEnum getTransactionWay() {
        return transactionWay;
    }

    public void setTransactionWay(TransactionWayEnum transactionWay) {
        this.transactionWay = transactionWay;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public WechatPayDto getWechatPayDto() {
        return wechatPayDto;
    }

    public void setWechatPayDto(WechatPayDto wechatPayDto) {
        this.wechatPayDto = wechatPayDto;
    }

    public AlipayTradeAppPayResponse getAliPayDto() {
        return aliPayDto;
    }

    public void setAliPayDto(AlipayTradeAppPayResponse aliPayDto) {
        this.aliPayDto = aliPayDto;
    }
}
