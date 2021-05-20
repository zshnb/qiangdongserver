package com.qiangdong.reader.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "sms")
public class SmsConfig {

    private String accessId;
    private String accessSecret;
    private String signName;
    private String identifyingTempleteCode;
    private String product;
    private String domain;

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getIdentifyingTempleteCode() {
        return identifyingTempleteCode;
    }

    public void setIdentifyingTempleteCode(String identifyingTempleteCode) {
        this.identifyingTempleteCode = identifyingTempleteCode;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
