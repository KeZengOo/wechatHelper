package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/18
 */
@ApiModel(value = "月目标")
public class MonthTargetResponseBean implements Serializable{
    private static final long serialVersionUID = 8441881770180469358L;

    @ApiModelProperty(value = "月目标微信人数")
    private Integer monthTargetWechatNum;

    @ApiModelProperty(value = "月目标微信人次")
    private Integer monthTargetWechatCount;

    @ApiModelProperty(value = "月目标短信人数")
    private Integer monthTargetImSum;

    @ApiModelProperty(value = "月目标短信人次")
    private Integer monthTargetImCount;

    @ApiModelProperty(value = "月目标邮件人数")
    private Integer monthTargetEmailNum;

    @ApiModelProperty(value = "月目标邮件人次")
    private Integer monthTargetEmailCount;

    @ApiModelProperty(value = "月目标电话人数")
    private Integer monthTargetCallSum;

    @ApiModelProperty(value = "月目标电话人次")
    private Integer monthTargetCallCount;

    @ApiModelProperty(value = "月目标电话时长，单位秒")
    private Integer monthTargetCallTime;

    @ApiModelProperty(value = "月目标会议场数")
    private Integer monthTargetMeetingSum;

    @ApiModelProperty(value = "月目标会议时长")
    private Integer monthTargetMeetingTime;

    @ApiModelProperty(value = "月目标会议人数")
    private Integer monthTargetMeetingPersonSum;

    @ApiModelProperty(value = "月目标会议人次")
    private Integer monthTargetMeetingPersonCount;


    public Integer getMonthTargetWechatNum() {
        return monthTargetWechatNum;
    }

    public void setMonthTargetWechatNum(Integer monthTargetWechatNum) {
        this.monthTargetWechatNum = monthTargetWechatNum;
    }

    public Integer getMonthTargetWechatCount() {
        return monthTargetWechatCount;
    }

    public void setMonthTargetWechatCount(Integer monthTargetWechatCount) {
        this.monthTargetWechatCount = monthTargetWechatCount;
    }

    public Integer getMonthTargetImSum() {
        return monthTargetImSum;
    }

    public void setMonthTargetImSum(Integer monthTargetImSum) {
        this.monthTargetImSum = monthTargetImSum;
    }

    public Integer getMonthTargetImCount() {
        return monthTargetImCount;
    }

    public void setMonthTargetImCount(Integer monthTargetImCount) {
        this.monthTargetImCount = monthTargetImCount;
    }

    public Integer getMonthTargetEmailNum() {
        return monthTargetEmailNum;
    }

    public void setMonthTargetEmailNum(Integer monthTargetEmailNum) {
        this.monthTargetEmailNum = monthTargetEmailNum;
    }

    public Integer getMonthTargetEmailCount() {
        return monthTargetEmailCount;
    }

    public void setMonthTargetEmailCount(Integer monthTargetEmailCount) {
        this.monthTargetEmailCount = monthTargetEmailCount;
    }

    public Integer getMonthTargetCallSum() {
        return monthTargetCallSum;
    }

    public void setMonthTargetCallSum(Integer monthTargetCallSum) {
        this.monthTargetCallSum = monthTargetCallSum;
    }

    public Integer getMonthTargetCallCount() {
        return monthTargetCallCount;
    }

    public void setMonthTargetCallCount(Integer monthTargetCallCount) {
        this.monthTargetCallCount = monthTargetCallCount;
    }

    public Integer getMonthTargetCallTime() {
        return monthTargetCallTime;
    }

    public void setMonthTargetCallTime(Integer monthTargetCallTime) {
        this.monthTargetCallTime = monthTargetCallTime;
    }

    public Integer getMonthTargetMeetingSum() {
        return monthTargetMeetingSum;
    }

    public void setMonthTargetMeetingSum(Integer monthTargetMeetingSum) {
        this.monthTargetMeetingSum = monthTargetMeetingSum;
    }

    public Integer getMonthTargetMeetingTime() {
        return monthTargetMeetingTime;
    }

    public void setMonthTargetMeetingTime(Integer monthTargetMeetingTime) {
        this.monthTargetMeetingTime = monthTargetMeetingTime;
    }

    public Integer getMonthTargetMeetingPersonSum() {
        return monthTargetMeetingPersonSum;
    }

    public void setMonthTargetMeetingPersonSum(Integer monthTargetMeetingPersonSum) {
        this.monthTargetMeetingPersonSum = monthTargetMeetingPersonSum;
    }

    public Integer getMonthTargetMeetingPersonCount() {
        return monthTargetMeetingPersonCount;
    }

    public void setMonthTargetMeetingPersonCount(Integer monthTargetMeetingPersonCount) {
        this.monthTargetMeetingPersonCount = monthTargetMeetingPersonCount;
    }
}
