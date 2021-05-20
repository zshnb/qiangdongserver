package com.qiangdong.reader.utils;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.qiangdong.reader.config.AliPayConfig;
import com.qiangdong.reader.exception.InternalException;
import com.qiangdong.reader.request.pay.AliPayRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class AliPayUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliPayUtil.class);

    @Autowired
    private AliPayConfig aliPayConfig;

    @Autowired
    private AlipayClient aliPayClient;

    public AlipayTradeAppPayResponse appPay(AliPayRequest request) {
        AlipayTradeAppPayRequest aliPayRequest = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("wall_hole_pay");
        model.setSubject(request.getSubject());
        model.setOutTradeNo(request.getOutTradeNo());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(request.getTotalAmount());
        model.setProductCode("QUICK_MSECURITY_PAY");

        aliPayRequest.setBizModel(model);
        aliPayRequest.setNotifyUrl(aliPayConfig.getNotifyUrl());
        AlipayTradeAppPayResponse response = null;
        try {
            response = aliPayClient.sdkExecute(aliPayRequest);
        } catch (AlipayApiException e) {
            LOGGER.error("支付宝支付异常", e);
            throw new InternalException("支付宝支付异常");
        }
        if (!response.isSuccess()) {
            LOGGER.error("支付宝支付异常，{}", JSONUtil.toJsonPrettyStr(response));
            throw new InternalException("支付宝支付异常");
        }
        return response;
    }
}
