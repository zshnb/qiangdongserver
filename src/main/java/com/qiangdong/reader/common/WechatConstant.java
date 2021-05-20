package com.qiangdong.reader.common;


public class WechatConstant {

    public final static String SUCCESS = "SUCCESS";

    public final static String FAIL = "FAIL";

    /**
     * 错误代码，系统错误
     */
    public final static String SYSTEM_ERROR = "SYSTEMERROR";

    /**
     * 错误代码，此交易订单号不存在
     */
    public final static String ORDER_NOT_EXIST = "ORDERNOTEXIST";

    /**
     * 错误代码，退款订单查询失败
     */
    public final static String REFUND_NOT_EXIST = "REFUNDNOTEXIST";

    /**
     * 统一下单接口
     */
    public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 订单查询
     */
    public final static String ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

    /**
     * 申请退款
     */
    public final static String PAY_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 退款查询
     */
    public final static String PAY_REFUND_QUERY_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
}
