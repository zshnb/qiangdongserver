package com.qiangdong.reader.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiangdong.reader.config.AliPayConfig;
import com.qiangdong.reader.config.WechatPayConfig;
import com.qiangdong.reader.dao.UserCreditRecordMapper;
import com.qiangdong.reader.dao.UserMapper;
import com.qiangdong.reader.dto.pay.PayOrderDto;
import com.qiangdong.reader.entity.User;
import com.qiangdong.reader.entity.UserCreditRecord;
import com.qiangdong.reader.enums.user_credit_record.UserCreditRecordStatusEnum;
import com.qiangdong.reader.exception.InvalidArgumentException;
import com.qiangdong.reader.request.pay.ApplyCallbackRequest;
import com.qiangdong.reader.request.pay.PayOrderRequest;
import com.qiangdong.reader.request.pay.WeChatNotifyRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.response.pay.ThirdPartyPayOrderResponse;
import com.qiangdong.reader.utils.IPUtil;
import com.qiangdong.reader.utils.PayUtil;
import com.qiangdong.reader.utils.WechatPayUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PayServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayServiceImpl.class);

    private final PayUtil payUtil;
    private final UserCreditRecordMapper userCreditRecordMapper;
    private final UserMapper userMapper;
    private final AliPayConfig aliPayConfig;
    private final WechatPayUtil wechatPayUtil;
    private final WechatPayConfig wechatPayConfig;

    public PayServiceImpl(PayUtil payUtil, UserCreditRecordMapper userCreditRecordMapper, UserMapper userMapper,
                          AliPayConfig aliPayConfig, WechatPayUtil wechatPayUtil, WechatPayConfig wechatPayConfig) {
        this.payUtil = payUtil;
        this.userCreditRecordMapper = userCreditRecordMapper;
        this.userMapper = userMapper;
        this. aliPayConfig = aliPayConfig;
        this.wechatPayUtil = wechatPayUtil;
        this.wechatPayConfig = wechatPayConfig;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Response<PayOrderDto> payOrder(PayOrderRequest request, HttpServletRequest httpServletRequest) {
        int coin;
        switch (request.getDevice()) {
            case IOS:
                coin = request.getPrice().intValue() * 7;
                break;
            case ANDROID:
                coin = request.getPrice().intValue() * 10;
                break;
            default:
                throw new InvalidArgumentException("无效的设备类型");
        }
        PayOrderDto payOrderDto = new PayOrderDto();
        String ip = IPUtil.getIpAddr(httpServletRequest);
        ThirdPartyPayOrderResponse payOrderResponse = payUtil.thirdPartyPayOrder(request, ip);

        UserCreditRecord userCreditRecord = new UserCreditRecord();
        BeanUtils.copyProperties(request, userCreditRecord);
        userCreditRecord.setOrderNumber(Long.valueOf(payOrderResponse.getOrderNumber()));
        userCreditRecord.setCoin(coin);
        userCreditRecord.setDescription(payOrderResponse.getSubject() + payOrderResponse.getTradeType());
        userCreditRecord.setStatus(UserCreditRecordStatusEnum.PENDING);
        userCreditRecordMapper.insert(userCreditRecord);
        BeanUtils.copyProperties(payOrderResponse, payOrderDto);
        return Response.ok(payOrderDto);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public String alipayCallback(HttpServletRequest request) {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        LOGGER.info("alipay_callback params : {}", JSONUtil.toJsonPrettyStr(params));
        //回调的待验签字符串
        //编码格式
        String charset = "UTF-8";
        //支付宝公钥
        String publicKey = aliPayConfig.getAliKey();
        //签名方式
        String signType = "RSA2";
        //验签方法
        boolean signVerified;
        try {
            //调用SDK验证签名
            signVerified = AlipaySignature.rsaCheckV1(params, publicKey, charset, signType);
        } catch (AlipayApiException e) {
            LOGGER.error("======验签失败 ！", e);
            return "fail";
        }

        //系统订单号
        String outTradeNumber = params.get("out_trade_no");
        UserCreditRecord userCreditRecord = userCreditRecordMapper.selectOne(new QueryWrapper<UserCreditRecord>()
            .eq("order_number", outTradeNumber)
            .eq("status", UserCreditRecordStatusEnum.PENDING));
        if (Objects.isNull(userCreditRecord)) {
            LOGGER.error("支付宝支付回调，订单未找到，订单号[{}]", outTradeNumber);
            return null;
        }
        //如果交易状态为订单已关闭，则修改订单状态为支付失败
        if (Objects.equals(params.get("trade_status"), "TRADE_FINISHED")) {
            userCreditRecord.setStatus(UserCreditRecordStatusEnum.FAILURE);
            userCreditRecordMapper.updateById(userCreditRecord);
            return "success";
        }
        //如果交易成功，修改订单状态为支付成功
        userCreditRecord.setStatus(UserCreditRecordStatusEnum.SUCCESS);
        userCreditRecordMapper.updateById(userCreditRecord);
        User user = userMapper.selectById(userCreditRecord.getUserId());
        user.setCoin(user.getCoin() + userCreditRecord.getCoin());
        userMapper.updateById(user);
        return "success";
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public String wechatPayCallback(WeChatNotifyRequest request) {
        String resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
        LOGGER.info("微信支付回调 request:" + request.toString());
        Map map = BeanUtil.beanToMap(request, false, true);
        wechatPayUtil.getSign(map, wechatPayConfig.getKey());
        if (!Objects.equals(map.get("sign"), request.getSign())) {
            LOGGER.error(String.format("微信支付回调，验签失败：request-sign:[%s], getSign: [%s]", (request.getSign()), map.get("sign")));
            return resXml;
        }

        String outTradeNumber = request.getOut_trade_no();
        UserCreditRecord userCreditRecord = userCreditRecordMapper.selectOne(new QueryWrapper<UserCreditRecord>()
            .eq("order_number", outTradeNumber)
            .eq("status", UserCreditRecordStatusEnum.PENDING));
        if (Objects.isNull(userCreditRecord)) {
            LOGGER.error("微信支付支付回调，订单未找到，订单号[{}]", outTradeNumber);
            return resXml;
        }

        //金额校验
        if (!Objects.equals(Integer.valueOf(request.getTotal_fee()) / 100, userCreditRecord.getPrice().intValue())) {
            LOGGER.error("微信支付回调，订单金额与商户侧的订单金额不一致[{}]", request.toString());
            return resXml;
        }
        if (request.getResult_code().equals("SUCCESS")) {
            LOGGER.info("微信支付回调---成功");
            userCreditRecord.setStatus(UserCreditRecordStatusEnum.SUCCESS);
            userCreditRecordMapper.updateById(userCreditRecord);
            User user = userMapper.selectById(userCreditRecord.getUserId());
            user.setCoin(user.getCoin() + userCreditRecord.getCoin());
            userMapper.updateById(user);
        } else {
            userCreditRecord.setStatus(UserCreditRecordStatusEnum.FAILURE);
            userCreditRecordMapper.updateById(userCreditRecord);
        }
        return resXml;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Response<String> appleCallback(ApplyCallbackRequest request) {
        LOGGER.info("苹果支付回调 request:" + request.toString());
        String outTradeNumber = request.getOrderNumber();
        // TODO 校验苹果票据
        JSONObject iapResult = payUtil.verifyReceipt(request.getReceipt());
        if (iapResult == null) {
            throw new InvalidArgumentException("票据校验错误");
        }
        UserCreditRecord userCreditRecord = userCreditRecordMapper.selectOne(new QueryWrapper<UserCreditRecord>()
            .eq("order_number", outTradeNumber)
            .eq("status", UserCreditRecordStatusEnum.PENDING)
            .eq("user_id", request.getUserId()));
        if (Objects.isNull(userCreditRecord)) {
            LOGGER.error(String.format("苹果支付回调，订单未找到，订单号:[%s]", outTradeNumber));
            throw new InvalidArgumentException("支付订单不存在");
        }
        LOGGER.info("苹果支付回调---成功");
        userCreditRecord.setStatus(UserCreditRecordStatusEnum.SUCCESS);
        userCreditRecordMapper.updateById(userCreditRecord);
        User user = userMapper.selectById(userCreditRecord.getUserId());
        user.setCoin(user.getCoin() + userCreditRecord.getCoin());
        userMapper.updateById(user);
        return Response.ok();
    }
}
