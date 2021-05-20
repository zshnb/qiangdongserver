package com.qiangdong.reader.controller;


import com.qiangdong.reader.dto.pay.PayOrderDto;
import com.qiangdong.reader.request.pay.ApplyCallbackRequest;
import com.qiangdong.reader.request.pay.PayOrderRequest;
import com.qiangdong.reader.request.pay.WeChatNotifyRequest;
import com.qiangdong.reader.response.Response;
import com.qiangdong.reader.serviceImpl.PayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayServiceImpl payService;

    @PostMapping("/order")
    public Response<PayOrderDto> payOrder(@RequestBody PayOrderRequest request,
                                          HttpServletRequest httpServletRequest) {
        return payService.payOrder(request, httpServletRequest);
    }

    @PostMapping(value = "/alipay/callback")
    public String aliPayCallback(HttpServletRequest request){
        return payService.alipayCallback(request);
    }

    @PostMapping(value = "/wechat/callback",
        consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE},
        produces = MediaType.APPLICATION_XML_VALUE)
    public String wechatCallback(@RequestBody WeChatNotifyRequest request) {
        return payService.wechatPayCallback(request);
    }

    /**
     * 苹果 in-app purchase
     */
    @PostMapping("/apple/callback")
    public Response<String> appleCallback(@RequestBody ApplyCallbackRequest request) {
        return payService.appleCallback(request);
    }
}
