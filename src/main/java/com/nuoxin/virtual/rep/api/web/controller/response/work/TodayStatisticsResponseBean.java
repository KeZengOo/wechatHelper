package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/12
 */
@ApiModel(value = "今日通话情况统计")
public class TodayStatisticsResponseBean implements Serializable{
    private static final long serialVersionUID = 8880700649104706377L;

    @ApiModelProperty(value = "拜访客户人数")
    private Integer visitSum = 0;

    @ApiModelProperty(value = "拜访客户目标人数")
    private Integer targetVisitSum = 0;

    @ApiModelProperty(value = "拜访客户人次")
    private Integer visitCount = 0;

    @ApiModelProperty(value = "拜访客户目标人次")
    private Integer targetVisitCount = 0;


    @ApiModelProperty(value = "呼出人数")
    private Integer callSum = 0;

    @ApiModelProperty(value = "目标呼出人数")
    private Integer targetCallSum = 0;

    @ApiModelProperty(value = "呼出人次")
    private Integer callCount = 0;

    @ApiModelProperty(value = "目标呼出人次")
    private Integer targetCallCount = 0;

    @ApiModelProperty(value = "通话总时长，单位是秒")
    private Integer callTotalTime = 0;

//    @ApiModelProperty(value = "通话平均时长，单位是秒")
//    private Integer callAvgTime;


    @ApiModelProperty(value = "微信人数")
    private Integer wechatSum = 0;

    @ApiModelProperty(value = "微信人次")
    private Integer wechatCount = 0;

    @ApiModelProperty(value = "微信目标人数")
    private Integer targetWechatSum = 0;

    @ApiModelProperty(value = "目标微信人次")
    private Integer targetWechatCount = 0;

    @ApiModelProperty(value = "短信人数")
    private Integer imSum = 0;

    @ApiModelProperty(value = "短信人次")
    private Integer imCount = 0;

    @ApiModelProperty(value = "短信目标人数")
    private Integer targetImSum = 0;

    @ApiModelProperty(value = "目标短信人次")
    private Integer targetImCount = 0;


    @ApiModelProperty(value = "邮件人数")
    private Integer emailSum = 0;

    @ApiModelProperty(value = "邮件目标人数")
    private Integer targetEmailSum = 0;

    @ApiModelProperty(value = "目标邮件人次")
    private Integer targetEmailCount = 0;


    @ApiModelProperty(value = "会议人次")
    private Integer meetingCount = 0;


    @ApiModelProperty(value = "会议总时长")
    private Integer meetingTotalTime = 0;

    @ApiModelProperty(value = "参加会议的人数")
    private Integer meetingTotalPersonNum = 0;

    @ApiModelProperty(value = "参加会议的人次")
    private Integer meetingTotalPersonCount = 0;

    @ApiModelProperty(value = "平均每人的参会时间")
    private String meetingAvgTime = "0.0";


    public Integer getVisitSum() {
        return visitSum;
    }

    public void setVisitSum(Integer visitSum) {
        this.visitSum = visitSum;
    }

    public Integer getTargetVisitSum() {
        return targetVisitSum;
    }

    public void setTargetVisitSum(Integer targetVisitSum) {
        this.targetVisitSum = targetVisitSum;
    }

    public Integer getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Integer visitCount) {
        this.visitCount = visitCount;
    }

    public Integer getTargetVisitCount() {
        return targetVisitCount;
    }

    public void setTargetVisitCount(Integer targetVisitCount) {
        this.targetVisitCount = targetVisitCount;
    }

    public Integer getCallSum() {
        return callSum;
    }

    public void setCallSum(Integer callSum) {
        this.callSum = callSum;
    }

    public Integer getTargetCallSum() {
        return targetCallSum;
    }

    public void setTargetCallSum(Integer targetCallSum) {
        this.targetCallSum = targetCallSum;
    }

    public Integer getCallCount() {
        return callCount;
    }

    public void setCallCount(Integer callCount) {
        this.callCount = callCount;
    }

    public Integer getTargetCallCount() {
        return targetCallCount;
    }

    public void setTargetCallCount(Integer targetCallCount) {
        this.targetCallCount = targetCallCount;
    }

    public Integer getCallTotalTime() {
        return callTotalTime;
    }

    public void setCallTotalTime(Integer callTotalTime) {
        this.callTotalTime = callTotalTime;
    }

//    public Integer getCallAvgTime() {
//        return callAvgTime;
//    }
//
//    public void setCallAvgTime(Integer callAvgTime) {
//        this.callAvgTime = callAvgTime;
//    }

    public Integer getWechatSum() {
        return wechatSum;
    }

    public void setWechatSum(Integer wechatSum) {
        this.wechatSum = wechatSum;
    }

    public Integer getTargetWechatSum() {
        return targetWechatSum;
    }

    public void setTargetWechatSum(Integer targetWechatSum) {
        this.targetWechatSum = targetWechatSum;
    }

    public Integer getImSum() {
        return imSum;
    }

    public void setImSum(Integer imSum) {
        this.imSum = imSum;
    }

    public Integer getTargetImSum() {
        return targetImSum;
    }

    public void setTargetImSum(Integer targetImSum) {
        this.targetImSum = targetImSum;
    }

    public Integer getEmailSum() {
        return emailSum;
    }

    public void setEmailSum(Integer emailSum) {
        this.emailSum = emailSum;
    }

    public Integer getTargetEmailSum() {
        return targetEmailSum;
    }

    public void setTargetEmailSum(Integer targetEmailSum) {
        this.targetEmailSum = targetEmailSum;
    }

    public Integer getMeetingCount() {
        return meetingCount;
    }

    public void setMeetingCount(Integer meetingCount) {
        this.meetingCount = meetingCount;
    }

    public Integer getMeetingTotalTime() {
        return meetingTotalTime;
    }

    public void setMeetingTotalTime(Integer meetingTotalTime) {
        this.meetingTotalTime = meetingTotalTime;
    }

    public Integer getMeetingTotalPersonNum() {
        return meetingTotalPersonNum;
    }

    public void setMeetingTotalPersonNum(Integer meetingTotalPersonNum) {
        this.meetingTotalPersonNum = meetingTotalPersonNum;
    }

    public Integer getMeetingTotalPersonCount() {
        return meetingTotalPersonCount;
    }

    public void setMeetingTotalPersonCount(Integer meetingTotalPersonCount) {
        this.meetingTotalPersonCount = meetingTotalPersonCount;
    }

    public String getMeetingAvgTime() {
        return meetingAvgTime;
    }

    public void setMeetingAvgTime(String meetingAvgTime) {
        this.meetingAvgTime = meetingAvgTime;
    }

    public Integer getWechatCount() {
        return wechatCount;
    }

    public void setWechatCount(Integer wechatCount) {
        this.wechatCount = wechatCount;
    }

    public Integer getImCount() {
        return imCount;
    }

    public void setImCount(Integer imCount) {
        this.imCount = imCount;
    }

    public Integer getTargetWechatCount() {
        return targetWechatCount;
    }

    public void setTargetWechatCount(Integer targetWechatCount) {
        this.targetWechatCount = targetWechatCount;
    }

    public Integer getTargetImCount() {
        return targetImCount;
    }

    public void setTargetImCount(Integer targetImCount) {
        this.targetImCount = targetImCount;
    }

    public Integer getTargetEmailCount() {
        return targetEmailCount;
    }

    public void setTargetEmailCount(Integer targetEmailCount) {
        this.targetEmailCount = targetEmailCount;
    }
}
