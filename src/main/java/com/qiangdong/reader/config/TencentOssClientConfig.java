package com.qiangdong.reader.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TencentOssClientConfig {
    @Autowired
    private TencentOssConfig ossConfig;

    @Bean
    public COSClient cosClient() {
        COSCredentials cosCredentials = new BasicCOSCredentials(ossConfig.getSecretId(), ossConfig.getSecretKey());
        Region region = new Region(ossConfig.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        return new COSClient(cosCredentials, clientConfig);
    }
}
