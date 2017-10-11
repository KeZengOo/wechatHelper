package com.nuoxin.virtual.rep.api.config;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by fenggang on 9/18/17.
 */
@ConfigurationProperties(prefix = "aliyun")
@Component
public class AliyunConfig {

    private static Logger logger = LoggerFactory.getLogger(AliyunConfig.class);

    private String accessKeyId;
    private String accessKeySecret;
    private String ossAccessKeyId;
    private String ossAccessKeySecret;
    private String mnsEndpoint;
    private String uploadEndpoint;
    private String downloadEndpoint;
    private String bucketName;
    private Long downloadUrlExpiration;
    private String storagePath;
    private String styleName;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getMnsEndpoint() {
        return mnsEndpoint;
    }

    public void setMnsEndpoint(String mnsEndpoint) {
        this.mnsEndpoint = mnsEndpoint;
    }

    public String getUploadEndpoint() {
        return uploadEndpoint;
    }

    public void setUploadEndpoint(String uploadEndpoint) {
        this.uploadEndpoint = uploadEndpoint;
    }

    public String getDownloadEndpoint() {
        return downloadEndpoint;
    }

    public void setDownloadEndpoint(String downloadEndpoint) {
        this.downloadEndpoint = downloadEndpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public Long getDownloadUrlExpiration() {
        return downloadUrlExpiration;
    }

    public void setDownloadUrlExpiration(Long downloadUrlExpiration) {
        this.downloadUrlExpiration = downloadUrlExpiration;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getOssAccessKeyId() {
        return ossAccessKeyId;
    }

    public void setOssAccessKeyId(String ossAccessKeyId) {
        this.ossAccessKeyId = ossAccessKeyId;
    }

    public String getOssAccessKeySecret() {
        return ossAccessKeySecret;
    }

    public void setOssAccessKeySecret(String ossAccessKeySecret) {
        this.ossAccessKeySecret = ossAccessKeySecret;
    }

    @PostConstruct
    public void init() {
        logger.debug(JSON.toJSONString(this));
    }
}

