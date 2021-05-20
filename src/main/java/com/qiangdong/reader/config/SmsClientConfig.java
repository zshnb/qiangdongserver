package com.qiangdong.reader.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsClientConfig {

    @Autowired
    private SmsConfig smsConfig;

    @Bean
    public IAcsClient iAcsClient() throws ClientException {
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                smsConfig.getAccessId(), smsConfig.getAccessSecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi",
                "dysmsapi.aliyuncs.com");
        return new DefaultAcsClient(profile);
    }
}
