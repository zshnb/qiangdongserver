package com.qiangdong.reader.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.qiangdong.reader.dto.pay.WechatPayDto;
import com.qiangdong.reader.enums.common.TransactionWayEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.pay.AliPayRequest;
import com.qiangdong.reader.request.pay.PayOrderRequest;
import com.qiangdong.reader.response.pay.ThirdPartyPayOrderResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.Objects;
import javax.net.ssl.HttpsURLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class PayUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PayUtil.class);
    private final AliPayUtil aliPayUtil;
    private final SnowflakeUtil snowflakeUtil;
    private final WechatPayUtil wechatPayUtil;

    public PayUtil(AliPayUtil aliPayUtil,
                   SnowflakeUtil snowflakeUtil,
                   WechatPayUtil wechatPayUtil) {
        this.aliPayUtil = aliPayUtil;
        this.snowflakeUtil = snowflakeUtil;
        this.wechatPayUtil = wechatPayUtil;
    }

    /**
     * 调用第三方支付 - 预处理订单
     * 处理 APP
     */
    public ThirdPartyPayOrderResponse thirdPartyPayOrder(PayOrderRequest request, String ip) {
        ThirdPartyPayOrderResponse response = new ThirdPartyPayOrderResponse();
        String subject = "墙币";
        Long orderNumber = snowflakeUtil.generateSnowflakeId();
        String fee = request.getPrice().toString();
        String tradeType = "APP";
        if (Objects.equals(TransactionWayEnum.WECHAT, request.getTransactionWay())) {
            WechatPayDto wechatPayResult = wechatPayUtil.unifiedOrder(subject, orderNumber.toString(), request.getPrice().intValue() * 100, ip, tradeType);
            response.setWechatPayDto(wechatPayResult);
        } else if (Objects.equals(TransactionWayEnum.ALIPAY, request.getTransactionWay())) {
            AliPayRequest aliPayRequest = new AliPayRequest();
            aliPayRequest.setTotalAmount(fee);
            aliPayRequest.setSubject(subject);
            aliPayRequest.setOutTradeNo(orderNumber.toString());
            AlipayTradeAppPayResponse aliPayResponse = aliPayUtil.appPay(aliPayRequest);
            response.setAliPayDto(aliPayResponse);
        } else {
            throw new InvalidArgumentException("支付方式不存在");
        }
        response.setSubject(subject);
        response.setOrderNumber(orderNumber.toString());
        response.setTradeType(tradeType);
        return response;
    }

    public JSONObject verifyReceipt(String recepit) {
        return verifyReceipt("https://buy.itunes.apple.com/verifyReceipt", recepit);
    }

    public JSONObject verifyReceipt(String url, String receipt) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setAllowUserInteraction(false);
            PrintStream ps = new PrintStream(connection.getOutputStream());
            ps.print("{\"receipt-data\": \"" + receipt + "\"}");
            ps.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str;
            StringBuffer sb = new StringBuffer();
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
            String resultStr = sb.toString();
            JSONObject result = JSONUtil.parseObj(resultStr);
            if (result != null && result.getInt("status") == 21007) {
                return verifyReceipt("https://sandbox.itunes.apple.com/verifyReceipt", receipt);
            }
            return result;
        } catch (Exception e) {
            LOGGER.error(String.format("苹果票据校验错误:%s", e));
            return null;
        }
    }
}