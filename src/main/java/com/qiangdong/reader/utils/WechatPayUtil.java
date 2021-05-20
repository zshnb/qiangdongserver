package com.qiangdong.reader.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Maps;
import com.qiangdong.reader.common.WechatConstant;
import com.qiangdong.reader.config.WechatPayConfig;
import com.qiangdong.reader.dto.pay.WechatPayDto;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.pay.UnifiedOrderRequest;
import com.qiangdong.reader.response.pay.UnifiedOrderResponse;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class WechatPayUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatPayUtil.class);

    private final WechatPayConfig wechatPayConfig;
    private final HttpUtil httpUtil;

    public WechatPayUtil(WechatPayConfig wechatPayConfig, HttpUtil httpUtil) {
        this.wechatPayConfig = wechatPayConfig;
        this.httpUtil = httpUtil;
    }

    /**
     * 微信支付统一下单请求
     *
     * @param body           商品描述
     * @param orderNumber     商户订单号
     * @param totalFee       标价金额
     * @param clentIp 终端IP
     * @param tradeType      交易类型
     * @return UnifiedOrderResponse
     */
    public WechatPayDto unifiedOrder(String body, String orderNumber, Integer totalFee, String clentIp,
                                             String tradeType) {
        LOGGER.info("微信统一下单开始");
        try {
            Map<String, Object> params = buildUnifiedOrder(body, orderNumber, totalFee, clentIp, tradeType);
            JSONUtil.parse(params);
            System.out.println("params:" + params.toString());
            String result = httpUtil.post(WechatConstant.UNIFIED_ORDER_URL, null, HttpUtil.MediaType.XML, params);
            LOGGER.info(String.format("商品描述:%s,商户订单号:%s,标价金额:%s,终端IP:%s,交易类型:%s,返回结果:%s",
                body, orderNumber, totalFee, clentIp, tradeType, result));
            LOGGER.info("微信统一下单结束");
            UnifiedOrderResponse unifiedOrderResponse = BeanUtil.toBean(XmlUtil.xmlToMap(result), UnifiedOrderResponse.class);
            WechatPayDto wechatPayDto = buildWeChatPayParams(unifiedOrderResponse.getPrepay_id());
            if (Objects.isNull(unifiedOrderResponse) || !Objects
                .equals(unifiedOrderResponse.getReturn_code(), WechatConstant.SUCCESS)) {
                LOGGER.error("支付预订单构建失败");
                throw new InvalidArgumentException("微信统一下单异常");
            }
            return wechatPayDto;
        } catch (Exception e) {
            throw new InvalidArgumentException("微信统一下单异常");
        }
    }

    /**
     * 构建APP支付订单
     */
    private Map<String, Object> buildUnifiedOrder(String body, String outTradeNo, Integer totalFee, String clientIp,
                                  String tradeType) {
        UnifiedOrderRequest unifiedorderRequest = new UnifiedOrderRequest();
        unifiedorderRequest.setAppid((wechatPayConfig.getAppId()));
        unifiedorderRequest.setMch_id(wechatPayConfig.getMchId());
        unifiedorderRequest.setNotify_url(wechatPayConfig.getNotifyUrl());
        unifiedorderRequest.setNonce_str(IdUtil.fastSimpleUUID());
        unifiedorderRequest.setBody(body);
        unifiedorderRequest.setOut_trade_no(outTradeNo);
        unifiedorderRequest.setTotal_fee(totalFee);
        unifiedorderRequest.setSpbill_create_ip(clientIp);
        unifiedorderRequest.setTrade_type(tradeType);
        Map<String, Object> map = BeanUtil.beanToMap(unifiedorderRequest, false, true);
        getSign(map, wechatPayConfig.getKey());
        return map;
    }

    public void getSign(Map<String, Object> map, String key) {
        Map<String, Object> params = new TreeMap<>(map);
        StringBuffer strb = new StringBuffer();
        params.forEach((k,v)-> {
            String notSign = "sign";
            if(!Objects.isNull(v) && !Objects.equals(notSign, k)){
                strb.append(k).append("=").append(v).append("&");
            }
        });
        strb.deleteCharAt(strb.length()-1);
        //签名数据
        String sign = md5(strb.toString() + "&key=" + key).toUpperCase();
        map.put("sign", sign);
    }

    public String md5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes(CharsetUtil.defaultCharsetName()));
            return HexUtil.encodeHexStr(md.digest());
        } catch (Exception e) {
            LOGGER.error(String.format("Failed to get the Message MD5 digest instance [%s]", data), e);
            throw new InvalidArgumentException("生成签名失败");
        }
    }

    /**
     * 构建微信支付返回结果
     *
     * @param prepayId  预支付交易会话标识
     * @return .WechatPay
     */
    private WechatPayDto buildWeChatPayParams(String prepayId) {
        WechatPayDto wechatPayDto = new WechatPayDto();
        wechatPayDto.setAppId(wechatPayConfig.getAppId());
        wechatPayDto.setPartnerId(wechatPayConfig.getMchId());
        wechatPayDto.setPrepayId(prepayId);
        wechatPayDto.setPackageValue("Sign=WXPay");
        wechatPayDto.setNonceStr(IdUtil.fastSimpleUUID());
        wechatPayDto.setTimeStamp(String.valueOf(cn.hutool.core.date.DateUtil.currentSeconds()));
        //签名
        Map<String, Object> map = Maps.newHashMap();
        map.put("appid", wechatPayDto.getAppId());
        map.put("partnerid", wechatPayDto.getPartnerId());
        map.put("prepayid", wechatPayDto.getPrepayId());
        map.put("package", wechatPayDto.getPackageValue());
        map.put("noncestr", wechatPayDto.getNonceStr());
        map.put("timestamp", wechatPayDto.getTimeStamp());
        getSign(map, wechatPayConfig.getKey());
        wechatPayDto.setPaySign(map.get("sign").toString());
        return wechatPayDto;
    }

}
