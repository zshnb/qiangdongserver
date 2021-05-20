package com.qiangdong.reader.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oss")
public class TencentOssConfig {
    private String secretId;

    private String secretKey;

    private String region;

    private String novelBucketName;

    private String novelDomain;

    private String comicBucketName;

    private String comicDomain;

    private String noticeBucketName;

    private String noticeDomain;

    private String apkFileBucketName;

    private String apkFileDomain;

    private String imageBucketName;

    private String imageDomain;

    public String getImageBucketName() {
        return imageBucketName;
    }

    public void setImageBucketName(String imageBucketName) {
        this.imageBucketName = imageBucketName;
    }

    public String getImageDomain() {
        return imageDomain;
    }

    public void setImageDomain(String imageDomain) {
        this.imageDomain = imageDomain;
    }

    public String getNovelDomain() {
        return novelDomain;
    }

    public void setNovelDomain(String novelDomain) {
        this.novelDomain = novelDomain;
    }

    public String getComicDomain() {
        return comicDomain;
    }

    public void setComicDomain(String comicDomain) {
        this.comicDomain = comicDomain;
    }

    public String getNoticeDomain() {
        return noticeDomain;
    }

    public void setNoticeDomain(String noticeDomain) {
        this.noticeDomain = noticeDomain;
    }

    public String getApkFileDomain() {
        return apkFileDomain;
    }

    public void setApkFileDomain(String apkFileDomain) {
        this.apkFileDomain = apkFileDomain;
    }

    public String getNoticeBucketName() {
        return noticeBucketName;
    }

    public void setNoticeBucketName(String noticeBucketName) {
        this.noticeBucketName = noticeBucketName;
    }

    public String getApkFileBucketName() {
        return apkFileBucketName;
    }

    public void setApkFileBucketName(String apkFileBucketName) {
        this.apkFileBucketName = apkFileBucketName;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getNovelBucketName() {
        return novelBucketName;
    }

    public void setNovelBucketName(String novelBucketName) {
        this.novelBucketName = novelBucketName;
    }

    public String getComicBucketName() {
        return comicBucketName;
    }

    public void setComicBucketName(String comicBucketName) {
        this.comicBucketName = comicBucketName;
    }
}
