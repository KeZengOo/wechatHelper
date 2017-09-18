package com.nuoxin.virtual.rep.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by fenggang on 9/18/17.
 */
@ConfigurationProperties(prefix = "aliyun")
@Component
public class SmsConfig {

    private String accessKeyId;
    private String accessKeySecret;
    private String mnsEndpoint;

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
}

