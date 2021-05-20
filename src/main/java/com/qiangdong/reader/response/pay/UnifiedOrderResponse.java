package com.qiangdong.reader.response.pay;

import java.io.Serializable;

/**
 * 统一下单响应结果
 */
public class UnifiedOrderResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**返回状态码*/
    private String return_code;

    /**返回信息*/
    private String return_msg;

    /**appid*/
    private String appid;

    /**mch_id*/
    private String mch_id;

    /**device_info*/
    private String device_info;

    /**nonce_str*/
    private String nonce_str;

    /**sign*/
    private String sign;

    /**result_code*/
    private String result_code;

    /**err_code*/
    private String err_code;

    /**err_code_des*/
    private String err_code_des;

    /**交易类型	*/
    private String trade_type;

    /**预支付交易会话标识*/
    private String prepay_id;

    /**二维码链接(小程序,JsApi)*/
    private String code_url;

    /**支付跳转链接(h5)*/
    private String mweb_url;

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

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }

    public String getMweb_url() {
        return mweb_url;
    }

    public void setMweb_url(String mweb_url) {
        this.mweb_url = mweb_url;
    }

    @Override
    public String toString() {
        return "UnifiedOrderResponse{" +
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
            ", trade_type='" + trade_type + '\'' +
            ", prepay_id='" + prepay_id + '\'' +
            ", code_url='" + code_url + '\'' +
            ", mweb_url='" + mweb_url + '\'' +
            '}';
    }
}
