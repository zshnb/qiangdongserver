package com.qiangdong.reader.request.pay;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信回调
 * @author xieze
 * @date 2019/8/8 10:57
 */
@XmlRootElement(name = "xml")
public class WeChatNotifyRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    /**返回状态码*/
    private String return_code;
    /**返回信息*/
    private String return_msg;
    /**appid*/
    private String appid;
    /**mch_id*/
    private String mch_id;
    /**设备号*/
    private String device_info;
    /**nonce_str*/
    private String nonce_str;
    /**sign*/
    private String sign;
    /**业务结果*/
    private String result_code;
    /**err_code*/
    private String err_code;
    /**err_code_des*/
    private String err_code_des;
    /**用户标识*/
    private String openid;
    /**是否关注公众账号*/
    private String is_subscribe;
    /**交易类型*/
    private String trade_type;
    /**付款银行*/
    private String bank_type;
    /**总金额*/
    private String total_fee;
    /**货币种类*/
    private String fee_type;
    /**现金支付金额*/
    private String cash_fee;
    /**现金支付货币类型*/
    private String cash_fee_type;
    /**代金券金额*/
    private String coupon_fee;
    /**代金券使用数量*/
    private String coupon_count;
    /**微信支付订单号*/
    private String transaction_id;
    /**商户订单号*/
    private String out_trade_no;
    /**附加数据*/
    private String attach;
    /**支付完成时间*/
    private String time_end;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(String cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getCash_fee_type() {
        return cash_fee_type;
    }

    public void setCash_fee_type(String cash_fee_type) {
        this.cash_fee_type = cash_fee_type;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getCoupon_fee() {
        return coupon_fee;
    }

    public void setCoupon_fee(String coupon_fee) {
        this.coupon_fee = coupon_fee;
    }

    public String getCoupon_count() {
        return coupon_count;
    }

    public void setCoupon_count(String coupon_count) {
        this.coupon_count = coupon_count;
    }

    @Override
    public String toString() {
        return "WeChatNotifyRequest{" +
            "return_code='" + return_code + '\'' +
            ", return_msg='" + return_msg + '\'' +
            ", appid='" + appid + '\'' +
            ", mch_id='" + mch_id + '\'' +
            ", device_info='" + device_info + '\'' +
            ", nonce_str='" + nonce_str + '\'' +
            ", sign='" + sign + '\'' +
            ", result_code='" + result_code + '\'' +
            ", err_code='" + err_code + '\'' +
            ", err_code_des='" + err_code_des + '\'' +
            ", openid='" + openid + '\'' +
            ", trade_type='" + trade_type + '\'' +
            ", bank_type='" + bank_type + '\'' +
            ", total_fee='" + total_fee + '\'' +
            ", fee_type='" + fee_type + '\'' +
            ", cash_fee='" + cash_fee + '\'' +
            ", cash_fee_type='" + cash_fee_type + '\'' +
            ", transaction_id='" + transaction_id + '\'' +
            ", out_trade_no='" + out_trade_no + '\'' +
            ", attach='" + attach + '\'' +
            ", time_end='" + time_end + '\'' +
            '}';
    }
}
