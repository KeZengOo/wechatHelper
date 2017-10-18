package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import sun.awt.SunHints;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/18
 */
@ApiModel(value = "周目标")
public class WeekTargetResponseBean implements Serializable{
    private static final long serialVersionUID = -7267962557577569936L;

    @ApiModelProperty(value = "周目标微信人数")
    private Integer weekTargetWechatNum;

    @ApiModelProperty(value = "周目标微信人次")
    private Integer weekTargetWechatCount;

    @ApiModelProperty(value = "周目标短信人数")
    private Integer weekTargetImSum;

    @ApiModelProperty(value = "周目标短信人次")
    private Integer weekTargetImCount;

    @ApiModelProperty(value = "周目标邮件人数")
    private Integer weekTargetEmailNum;

    @ApiModelProperty(value = "周目标邮件人次")
    private Integer weekTargetEmailCount;

    @ApiModelProperty(value = "周目标电话人数")
    private Integer weekTargetCallSum;

    @ApiModelProperty(value = "周目标电话人次")
    private Integer weekTargetCallCount;

    @ApiModelProperty(value = "周目标电话时长，单位秒")
    private Integer weekTargetCallTime;

    @ApiModelProperty(value = "周目标会议场数")
    private Integer weekTargetMeetingSum;

    @ApiModelProperty(value = "周目标会议时长")
    private Integer weekTargetMeetingTime;

    @ApiModelProperty(value = "周目标会议人数")
    private Integer weekTargetMeetingPersonSum;

    @ApiModelProperty(value = "周目标会议人次")
    private Integer weekTargetMeetingPersonCount;


    public Integer getWeekTargetWechatNum() {
        return weekTargetWechatNum;
    }

    public void setWeekTargetWechatNum(Integer weekTargetWechatNum) {
        this.weekTargetWechatNum = weekTargetWechatNum;
    }

    public Integer getWeekTargetWechatCount() {
        return weekTargetWechatCount;
    }

    public void setWeekTargetWechatCount(Integer weekTargetWechatCount) {
        this.weekTargetWechatCount = weekTargetWechatCount;
    }

    public Integer getWeekTargetImSum() {
        return weekTargetImSum;
    }

    public void setWeekTargetImSum(Integer weekTargetImSum) {
        this.weekTargetImSum = weekTargetImSum;
    }

    public Integer getWeekTargetImCount() {
        return weekTargetImCount;
    }

    public void setWeekTargetImCount(Integer weekTargetImCount) {
        this.weekTargetImCount = weekTargetImCount;
    }

    public Integer getWeekTargetEmailNum() {
        return weekTargetEmailNum;
    }

    public void setWeekTargetEmailNum(Integer weekTargetEmailNum) {
        this.weekTargetEmailNum = weekTargetEmailNum;
    }

    public Integer getWeekTargetEmailCount() {
        return weekTargetEmailCount;
    }

    public void setWeekTargetEmailCount(Integer weekTargetEmailCount) {
        this.weekTargetEmailCount = weekTargetEmailCount;
    }

    public Integer getWeekTargetCallSum() {
        return weekTargetCallSum;
    }

    public void setWeekTargetCallSum(Integer weekTargetCallSum) {
        this.weekTargetCallSum = weekTargetCallSum;
    }

    public Integer getWeekTargetCallCount() {
        return weekTargetCallCount;
    }

    public void setWeekTargetCallCount(Integer weekTargetCallCount) {
        this.weekTargetCallCount = weekTargetCallCount;
    }

    public Integer getWeekTargetCallTime() {
        return weekTargetCallTime;
    }

    public void setWeekTargetCallTime(Integer weekTargetCallTime) {
        this.weekTargetCallTime = weekTargetCallTime;
    }

    public Integer getWeekTargetMeetingSum() {
        return weekTargetMeetingSum;
    }

    public void setWeekTargetMeetingSum(Integer weekTargetMeetingSum) {
        this.weekTargetMeetingSum = weekTargetMeetingSum;
    }

    public Integer getWeekTargetMeetingTime() {
        return weekTargetMeetingTime;
    }

    public void setWeekTargetMeetingTime(Integer weekTargetMeetingTime) {
        this.weekTargetMeetingTime = weekTargetMeetingTime;
    }

    public Integer getWeekTargetMeetingPersonSum() {
        return weekTargetMeetingPersonSum;
    }

    public void setWeekTargetMeetingPersonSum(Integer weekTargetMeetingPersonSum) {
        this.weekTargetMeetingPersonSum = weekTargetMeetingPersonSum;
    }

    public Integer getWeekTargetMeetingPersonCount() {
        return weekTargetMeetingPersonCount;
    }

    public void setWeekTargetMeetingPersonCount(Integer weekTargetMeetingPersonCount) {
        this.weekTargetMeetingPersonCount = weekTargetMeetingPersonCount;
    }
}
