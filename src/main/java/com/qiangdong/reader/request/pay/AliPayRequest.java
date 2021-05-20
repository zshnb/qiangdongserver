package com.qiangdong.reader.request.pay;

/**
 * 支付宝 - APP支付请求参数
 */
public class AliPayRequest {
    /** 支付金额 */
    private String totalAmount;
    /** 订单标题 */
    private String subject;
    /** 商户订单号 */
    private String outTradeNo;
    /** 销售产品码 */
    private String productCode;

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
