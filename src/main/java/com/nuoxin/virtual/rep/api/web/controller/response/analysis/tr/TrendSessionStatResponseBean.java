package com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by fenggang on 10/17/17.
 */
@ApiModel
public class TrendSessionStatResponseBean extends TrendResponseBean {
    private static final long serialVersionUID = -6705566658957915262L;

    @ApiModelProperty(value = "微信")
    private Integer wechat = 0;
    @ApiModelProperty(value = "短信")
    private Integer sms = 0;
    @ApiModelProperty(value = "邮件")
    private Integer email = 0;

    public Integer getWechat() {
        return wechat;
    }

    public void setWechat(Integer wechat) {
        this.wechat = wechat;
    }

    public Integer getSms() {
        return sms;
    }

    public void setSms(Integer sms) {
        this.sms = sms;
    }

    public Integer getEmail() {
        return email;
    }

    public void setEmail(Integer email) {
        this.email = email;
    }
}
