package com.qiangdong.reader.request.pay;

import com.qiangdong.reader.enums.common.DeviceEnum;
import com.qiangdong.reader.enums.common.TransactionWayEnum;
import com.qiangdong.reader.request.BaseRequest;

import java.math.BigDecimal;

public class PayOrderRequest extends BaseRequest {
    private TransactionWayEnum transactionWay = TransactionWayEnum.NONE;
    private BigDecimal price = new BigDecimal(0);
    private DeviceEnum device = DeviceEnum.NONE;

    public TransactionWayEnum getTransactionWay() {
        return transactionWay;
    }

    public void setTransactionWay(TransactionWayEnum transactionWay) {
        this.transactionWay = transactionWay;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public DeviceEnum getDevice() {
        return device;
    }

    public void setDevice(DeviceEnum device) {
        this.device = device;
    }
}
