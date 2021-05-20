package com.qiangdong.reader.request.withdraw_record;

import com.qiangdong.reader.request.BaseRequest;
import java.math.BigDecimal;

public class ApplyWithdrawRequest extends BaseRequest {
    private BigDecimal money = new BigDecimal("0.00");

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
