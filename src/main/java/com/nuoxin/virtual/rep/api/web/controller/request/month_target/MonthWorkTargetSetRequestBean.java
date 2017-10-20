package com.nuoxin.virtual.rep.api.web.controller.request.month_target;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/18
 */
@ApiModel(value = "月工作目标请求类")
public class MonthWorkTargetSetRequestBean implements Serializable{
    private static final long serialVersionUID = 7939224146015146634L;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "医生等级")
    private String level;

    @ApiModelProperty(value = "月目标电话个数")
    private Integer monthTelNum;

    @ApiModelProperty(value = "月目标电话总时长，单位秒")
    private Integer monthTelTime;

    @ApiModelProperty(value = "月目标电话人数")
    private Integer monthTelPerson;

    @ApiModelProperty(value = "月目标电话每次平均时长，单位秒")
    private Integer monthTelAvgTime;


    @ApiModelProperty(value = "月目标短信人数")
    private Integer monthImNum;

    @ApiModelProperty(value = "月目标短信人次")
    private Integer monthImCount;

    @ApiModelProperty(value = "月目标微信人数")
    private Integer monthWechatNum;

    @ApiModelProperty(value = "月目标微信人次")
    private Integer monthWechatCount;

    @ApiModelProperty(value = "月目标邮件人数")
    private Integer monthEmailNum;

    @ApiModelProperty(value = "月目标邮件人次")
    private Integer monthEmailCount;

    @ApiModelProperty(value = "月目标会议场次")
    private Integer monthMeetingNum;

    @ApiModelProperty(value = "月目标会议总时长，单位分钟")
    private Integer monthMeetingTime;

    @ApiModelProperty(value = "月目标会议参会人数")
    private Integer monthMeetingPersonSum;

    @ApiModelProperty(value = "月目标会议参会人次")
    private Integer monthMeetingPersonCount;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getMonthTelNum() {
        return monthTelNum;
    }

    public void setMonthTelNum(Integer monthTelNum) {
        this.monthTelNum = monthTelNum;
    }

    public Integer getMonthTelTime() {
        return monthTelTime;
    }

    public void setMonthTelTime(Integer monthTelTime) {
        this.monthTelTime = monthTelTime;
    }

    public Integer getMonthTelPerson() {
        return monthTelPerson;
    }

    public void setMonthTelPerson(Integer monthTelPerson) {
        this.monthTelPerson = monthTelPerson;
    }

    public Integer getMonthTelAvgTime() {
        return monthTelAvgTime;
    }

    public void setMonthTelAvgTime(Integer monthTelAvgTime) {
        this.monthTelAvgTime = monthTelAvgTime;
    }

    public Integer getMonthImNum() {
        return monthImNum;
    }

    public void setMonthImNum(Integer monthImNum) {
        this.monthImNum = monthImNum;
    }

    public Integer getMonthImCount() {
        return monthImCount;
    }

    public void setMonthImCount(Integer monthImCount) {
        this.monthImCount = monthImCount;
    }

    public Integer getMonthWechatNum() {
        return monthWechatNum;
    }

    public void setMonthWechatNum(Integer monthWechatNum) {
        this.monthWechatNum = monthWechatNum;
    }

    public Integer getMonthWechatCount() {
        return monthWechatCount;
    }

    public void setMonthWechatCount(Integer monthWechatCount) {
        this.monthWechatCount = monthWechatCount;
    }

    public Integer getMonthEmailNum() {
        return monthEmailNum;
    }

    public void setMonthEmailNum(Integer monthEmailNum) {
        this.monthEmailNum = monthEmailNum;
    }

    public Integer getMonthEmailCount() {
        return monthEmailCount;
    }

    public void setMonthEmailCount(Integer monthEmailCount) {
        this.monthEmailCount = monthEmailCount;
    }

    public Integer getMonthMeetingNum() {
        return monthMeetingNum;
    }

    public void setMonthMeetingNum(Integer monthMeetingNum) {
        this.monthMeetingNum = monthMeetingNum;
    }

    public Integer getMonthMeetingTime() {
        return monthMeetingTime;
    }

    public void setMonthMeetingTime(Integer monthMeetingTime) {
        this.monthMeetingTime = monthMeetingTime;
    }

    public Integer getMonthMeetingPersonSum() {
        return monthMeetingPersonSum;
    }

    public void setMonthMeetingPersonSum(Integer monthMeetingPersonSum) {
        this.monthMeetingPersonSum = monthMeetingPersonSum;
    }

    public Integer getMonthMeetingPersonCount() {
        return monthMeetingPersonCount;
    }

    public void setMonthMeetingPersonCount(Integer monthMeetingPersonCount) {
        this.monthMeetingPersonCount = monthMeetingPersonCount;
    }
}
