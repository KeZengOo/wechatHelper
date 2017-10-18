package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/18
 */
@ApiModel(value = "每天目标")
public class DayTargetResponseBean implements Serializable{
    private static final long serialVersionUID = -1673451274558192404L;

    @ApiModelProperty(value = "每天目标微信人数")
    private Integer dayTargetWechatNum;

    @ApiModelProperty(value = "每天目标微信人次")
    private Integer dayTargetWechatCount;

    @ApiModelProperty(value = "每天目标短信人数")
    private Integer dayTargetImSum;

    @ApiModelProperty(value = "每天目标短信人次")
    private Integer dayTargetImCount;

    @ApiModelProperty(value = "每天目标邮件人数")
    private Integer dayTargetEmailNum;

    @ApiModelProperty(value = "每天目标邮件人次")
    private Integer dayTargetEmailCount;

    @ApiModelProperty(value = "每天目标电话人数")
    private Integer dayTargetCallSum;

    @ApiModelProperty(value = "每天目标电话人次")
    private Integer dayTargetCallCount;

    @ApiModelProperty(value = "每天目标电话时长，单位秒")
    private Integer dayTargetCallTime;

    @ApiModelProperty(value = "每天目标会议场数")
    private Integer dayTargetMeetingSum;

    @ApiModelProperty(value = "每天目标会议时长")
    private Integer dayTargetMeetingTime;

    @ApiModelProperty(value = "每天目标会议人数")
    private Integer dayTargetMeetingPersonSum;

    @ApiModelProperty(value = "每天目标会议人次")
    private Integer dayTargetMeetingPersonCount;


    public Integer getDayTargetWechatNum() {
        return dayTargetWechatNum;
    }

    public void setDayTargetWechatNum(Integer dayTargetWechatNum) {
        this.dayTargetWechatNum = dayTargetWechatNum;
    }

    public Integer getDayTargetWechatCount() {
        return dayTargetWechatCount;
    }

    public void setDayTargetWechatCount(Integer dayTargetWechatCount) {
        this.dayTargetWechatCount = dayTargetWechatCount;
    }

    public Integer getDayTargetImSum() {
        return dayTargetImSum;
    }

    public void setDayTargetImSum(Integer dayTargetImSum) {
        this.dayTargetImSum = dayTargetImSum;
    }

    public Integer getDayTargetImCount() {
        return dayTargetImCount;
    }

    public void setDayTargetImCount(Integer dayTargetImCount) {
        this.dayTargetImCount = dayTargetImCount;
    }

    public Integer getDayTargetEmailNum() {
        return dayTargetEmailNum;
    }

    public void setDayTargetEmailNum(Integer dayTargetEmailNum) {
        this.dayTargetEmailNum = dayTargetEmailNum;
    }

    public Integer getDayTargetEmailCount() {
        return dayTargetEmailCount;
    }

    public void setDayTargetEmailCount(Integer dayTargetEmailCount) {
        this.dayTargetEmailCount = dayTargetEmailCount;
    }

    public Integer getDayTargetCallSum() {
        return dayTargetCallSum;
    }

    public void setDayTargetCallSum(Integer dayTargetCallSum) {
        this.dayTargetCallSum = dayTargetCallSum;
    }

    public Integer getDayTargetCallCount() {
        return dayTargetCallCount;
    }

    public void setDayTargetCallCount(Integer dayTargetCallCount) {
        this.dayTargetCallCount = dayTargetCallCount;
    }

    public Integer getDayTargetCallTime() {
        return dayTargetCallTime;
    }

    public void setDayTargetCallTime(Integer dayTargetCallTime) {
        this.dayTargetCallTime = dayTargetCallTime;
    }

    public Integer getDayTargetMeetingSum() {
        return dayTargetMeetingSum;
    }

    public void setDayTargetMeetingSum(Integer dayTargetMeetingSum) {
        this.dayTargetMeetingSum = dayTargetMeetingSum;
    }

    public Integer getDayTargetMeetingTime() {
        return dayTargetMeetingTime;
    }

    public void setDayTargetMeetingTime(Integer dayTargetMeetingTime) {
        this.dayTargetMeetingTime = dayTargetMeetingTime;
    }

    public Integer getDayTargetMeetingPersonSum() {
        return dayTargetMeetingPersonSum;
    }

    public void setDayTargetMeetingPersonSum(Integer dayTargetMeetingPersonSum) {
        this.dayTargetMeetingPersonSum = dayTargetMeetingPersonSum;
    }

    public Integer getDayTargetMeetingPersonCount() {
        return dayTargetMeetingPersonCount;
    }

    public void setDayTargetMeetingPersonCount(Integer dayTargetMeetingPersonCount) {
        this.dayTargetMeetingPersonCount = dayTargetMeetingPersonCount;
    }
}
