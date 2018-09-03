package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/18
 */
@ApiModel(value = "季度目标")
public class QuarterTargetResponseBean implements Serializable{
    private static final long serialVersionUID = -7983215280277080738L;

    @ApiModelProperty(value = "季度目标微信人数")
    private Integer quarterTargetWechatNum;

    @ApiModelProperty(value = "季度目标微信人次")
    private Integer quarterTargetWechatCount;

    @ApiModelProperty(value = "季度目标短信人数")
    private Integer quarterTargetImSum;

    @ApiModelProperty(value = "季度目标短信人次")
    private Integer quarterTargetImCount;

    @ApiModelProperty(value = "季度目标邮件人数")
    private Integer quarterTargetEmailNum;

    @ApiModelProperty(value = "季度目标邮件人次")
    private Integer quarterTargetEmailCount;

    @ApiModelProperty(value = "季度目标电话人数")
    private Integer quarterTargetCallSum;

    @ApiModelProperty(value = "季度目标电话人次")
    private Integer quarterTargetCallCount;

    @ApiModelProperty(value = "季度目标电话时长，单位秒")
    private Integer quarterTargetCallTime;

    @ApiModelProperty(value = "季度目标会议场数")
    private Integer quarterTargetMeetingSum;

    @ApiModelProperty(value = "季度目标会议时长")
    private Integer quarterTargetMeetingTime;

    @ApiModelProperty(value = "季度目标会议人数")
    private Integer quarterTargetMeetingPersonSum;

    @ApiModelProperty(value = "季度目标会议人次")
    private Integer quarterTargetMeetingPersonCount;

    public Integer getQuarterTargetWechatNum() {
        return quarterTargetWechatNum;
    }

    public void setQuarterTargetWechatNum(Integer quarterTargetWechatNum) {
        this.quarterTargetWechatNum = quarterTargetWechatNum;
    }

    public Integer getQuarterTargetWechatCount() {
        return quarterTargetWechatCount;
    }

    public void setQuarterTargetWechatCount(Integer quarterTargetWechatCount) {
        this.quarterTargetWechatCount = quarterTargetWechatCount;
    }

    public Integer getQuarterTargetImSum() {
        return quarterTargetImSum;
    }

    public void setQuarterTargetImSum(Integer quarterTargetImSum) {
        this.quarterTargetImSum = quarterTargetImSum;
    }

    public Integer getQuarterTargetImCount() {
        return quarterTargetImCount;
    }

    public void setQuarterTargetImCount(Integer quarterTargetImCount) {
        this.quarterTargetImCount = quarterTargetImCount;
    }

    public Integer getQuarterTargetEmailNum() {
        return quarterTargetEmailNum;
    }

    public void setQuarterTargetEmailNum(Integer quarterTargetEmailNum) {
        this.quarterTargetEmailNum = quarterTargetEmailNum;
    }

    public Integer getQuarterTargetEmailCount() {
        return quarterTargetEmailCount;
    }

    public void setQuarterTargetEmailCount(Integer quarterTargetEmailCount) {
        this.quarterTargetEmailCount = quarterTargetEmailCount;
    }

    public Integer getQuarterTargetCallSum() {
        return quarterTargetCallSum;
    }

    public void setQuarterTargetCallSum(Integer quarterTargetCallSum) {
        this.quarterTargetCallSum = quarterTargetCallSum;
    }

    public Integer getQuarterTargetCallCount() {
        return quarterTargetCallCount;
    }

    public void setQuarterTargetCallCount(Integer quarterTargetCallCount) {
        this.quarterTargetCallCount = quarterTargetCallCount;
    }

    public Integer getQuarterTargetCallTime() {
        return quarterTargetCallTime;
    }

    public void setQuarterTargetCallTime(Integer quarterTargetCallTime) {
        this.quarterTargetCallTime = quarterTargetCallTime;
    }

    public Integer getQuarterTargetMeetingSum() {
        return quarterTargetMeetingSum;
    }

    public void setQuarterTargetMeetingSum(Integer quarterTargetMeetingSum) {
        this.quarterTargetMeetingSum = quarterTargetMeetingSum;
    }

    public Integer getQuarterTargetMeetingTime() {
        return quarterTargetMeetingTime;
    }

    public void setQuarterTargetMeetingTime(Integer quarterTargetMeetingTime) {
        this.quarterTargetMeetingTime = quarterTargetMeetingTime;
    }

    public Integer getQuarterTargetMeetingPersonSum() {
        return quarterTargetMeetingPersonSum;
    }

    public void setQuarterTargetMeetingPersonSum(Integer quarterTargetMeetingPersonSum) {
        this.quarterTargetMeetingPersonSum = quarterTargetMeetingPersonSum;
    }

    public Integer getQuarterTargetMeetingPersonCount() {
        return quarterTargetMeetingPersonCount;
    }

    public void setQuarterTargetMeetingPersonCount(Integer quarterTargetMeetingPersonCount) {
        this.quarterTargetMeetingPersonCount = quarterTargetMeetingPersonCount;
    }
}
