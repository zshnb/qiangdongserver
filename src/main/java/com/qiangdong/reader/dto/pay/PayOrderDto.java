package com.qiangdong.reader.dto.pay;

import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.qiangdong.reader.response.pay.UnifiedOrderResponse;
import java.util.Map;

/**
 * 前端 APP SDK 需要的参数
 */
public class PayOrderDto {
    private String orderNumber;
    private WechatPayDto wechatPayDto;
    private AlipayTradeAppPayResponse aliPayDto;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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
