package com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 10/16/17.
 */
@ApiModel
public class TrendStatResponseBean implements Serializable {
    private static final long serialVersionUID = 172396389288114416L;

    @ApiModelProperty(value = "微信")
    private Integer wechat = 0;
    @ApiModelProperty(value = "短信")
    private Integer sms = 0;
    @ApiModelProperty(value = "邮件")
    private Integer email = 0;
    @ApiModelProperty(value = "接通数")
    private Integer connect = 0;
    @ApiModelProperty(value = "呼出次数")
    private Integer callout = 0;
    @ApiModelProperty(value = "时间（横坐标）")
    private Integer hour;

    @ApiModelProperty(value = "微信")
    private Integer wechatCount = 0;
    @ApiModelProperty(value = "短信")
    private Integer smsCount = 0;
    @ApiModelProperty(value = "邮件")
    private Integer emailCount = 0;

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

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getConnect() {
        return connect;
    }

    public void setConnect(Integer connect) {
        this.connect = connect;
    }

    public Integer getEmail() {
        return email;
    }

    public void setEmail(Integer email) {
        this.email = email;
    }

    public Integer getCallout() {
        return callout;
    }

    public void setCallout(Integer callout) {
        this.callout = callout;
    }

    public Integer getWechatCount() {
        return wechatCount;
    }

    public void setWechatCount(Integer wechatCount) {
        this.wechatCount = wechatCount;
    }

    public Integer getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(Integer smsCount) {
        this.smsCount = smsCount;
    }

    public Integer getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(Integer emailCount) {
        this.emailCount = emailCount;
    }
}
