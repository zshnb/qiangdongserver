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
                throw new InvalidArgumentException("?????????????????????");
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
        //???????????????POST??????????????????
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
        //???????????????????????????
        //????????????
        String charset = "UTF-8";
        //???????????????
        String publicKey = aliPayConfig.getAliKey();
        //????????????
        String signType = "RSA2";
        //????????????
        boolean signVerified;
        try {
            //??????SDK????????????
            signVerified = AlipaySignature.rsaCheckV1(params, publicKey, charset, signType);
        } catch (AlipayApiException e) {
            LOGGER.error("======???????????? ???", e);
            return "fail";
        }

        //???????????????
        String outTradeNumber = params.get("out_trade_no");
        UserCreditRecord userCreditRecord = userCreditRecordMapper.selectOne(new QueryWrapper<UserCreditRecord>()
            .eq("order_number", outTradeNumber)
            .eq("status", UserCreditRecordStatusEnum.PENDING));
        if (Objects.isNull(userCreditRecord)) {
            LOGGER.error("???????????????????????????????????????????????????[{}]", outTradeNumber);
            return null;
        }
        //???????????????????????????????????????????????????????????????????????????
        if (Objects.equals(params.get("trade_status"), "TRADE_FINISHED")) {
            userCreditRecord.setStatus(UserCreditRecordStatusEnum.FAILURE);
            userCreditRecordMapper.updateById(userCreditRecord);
            return "success";
        }
        //??????????????????????????????????????????????????????
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
        LOGGER.info("?????????????????? request:" + request.toString());
        Map map = BeanUtil.beanToMap(request, false, true);
        wechatPayUtil.getSign(map, wechatPayConfig.getKey());
        if (!Objects.equals(map.get("sign"), request.getSign())) {
            LOGGER.error(String.format("????????????????????????????????????request-sign:[%s], getSign: [%s]", (request.getSign()), map.get("sign")));
            return resXml;
        }

        String outTradeNumber = request.getOut_trade_no();
        UserCreditRecord userCreditRecord = userCreditRecordMapper.selectOne(new QueryWrapper<UserCreditRecord>()
            .eq("order_number", outTradeNumber)
            .eq("status", UserCreditRecordStatusEnum.PENDING));
        if (Objects.isNull(userCreditRecord)) {
            LOGGER.error("??????????????????????????????????????????????????????[{}]", outTradeNumber);
            return resXml;
        }

        //????????????
        if (!Objects.equals(Integer.valueOf(request.getTotal_fee()) / 100, userCreditRecord.getPrice().intValue())) {
            LOGGER.error("?????????????????????????????????????????????????????????????????????[{}]", request.toString());
            return resXml;
        }
        if (request.getResult_code().equals("SUCCESS")) {
            LOGGER.info("??????????????????---??????");
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
        LOGGER.info("?????????????????? request:" + request.toString());
        String outTradeNumber = request.getOrderNumber();
        // TODO ??????????????????
        JSONObject iapResult = payUtil.verifyReceipt(request.getReceipt());
        if (iapResult == null) {
            throw new InvalidArgumentException("??????????????????");
        }
        UserCreditRecord userCreditRecord = userCreditRecordMapper.selectOne(new QueryWrapper<UserCreditRecord>()
            .eq("order_number", outTradeNumber)
            .eq("status", UserCreditRecordStatusEnum.PENDING)
            .eq("user_id", request.getUserId()));
        if (Objects.isNull(userCreditRecord)) {
            LOGGER.error(String.format("????????????????????????????????????????????????:[%s]", outTradeNumber));
            throw new InvalidArgumentException("?????????????????????");
        }
        LOGGER.info("??????????????????---??????");
        userCreditRecord.setStatus(UserCreditRecordStatusEnum.SUCCESS);
        userCreditRecordMapper.updateById(userCreditRecord);
        User user = userMapper.selectById(userCreditRecord.getUserId());
        user.setCoin(user.getCoin() + userCreditRecord.getCoin());
        userMapper.updateById(user);
        return Response.ok();
    }
}
