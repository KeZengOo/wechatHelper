package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Create by tiancun on 2017/10/24
 */
@ApiModel(value = "坐席或者客户分析返回数据")
public class AnalysisResponseBean {

    @ApiModelProperty(value = "会议时长，单位分钟")
    private Integer meetingTime = 0;

    @ApiModelProperty(value = "微信阅读时长，单位秒")
    private Integer wechatTime = 0;

    @ApiModelProperty(value = "短信阅读时长，单位秒")
    private Integer imTime = 0;

    @ApiModelProperty(value = "邮件阅读时长，单位秒")
    private Integer emailTime = 0;

    @ApiModelProperty(value = "电话时长，单位秒")
    private Integer callTime = 0;


    public Integer getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(Integer meetingTime) {
        this.meetingTime = meetingTime;
    }

    public Integer getWechatTime() {
        return wechatTime;
    }

    public void setWechatTime(Integer wechatTime) {
        this.wechatTime = wechatTime;
    }

    public Integer getImTime() {
        return imTime;
    }

    public void setImTime(Integer imTime) {
        this.imTime = imTime;
    }

    public Integer getEmailTime() {
        return emailTime;
    }

    public void setEmailTime(Integer emailTime) {
        this.emailTime = emailTime;
    }

    public Integer getCallTime() {
        return callTime;
    }

    public void setCallTime(Integer callTime) {
        this.callTime = callTime;
    }
}
