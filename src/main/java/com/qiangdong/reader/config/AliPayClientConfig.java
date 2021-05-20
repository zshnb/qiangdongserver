package com.qiangdong.reader.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliPayClientConfig {

    private final AliPayConfig aliPayConfig;

    public AliPayClientConfig(AliPayConfig aliPayConfig) {
        this.aliPayConfig = aliPayConfig;
    }

    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(
                aliPayConfig.getGatewayUrl(),
                aliPayConfig.getAppId(),
                aliPayConfig.getPrivateKey(),
                aliPayConfig.getFormat(),
                aliPayConfig.getCharset(),
                aliPayConfig.getPublicKey(),
                aliPayConfig.getSignType());
    }
}
