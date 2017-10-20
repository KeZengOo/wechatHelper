package com.nuoxin.virtual.rep.api.web.controller.request;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/12
 */
@ApiModel(value = "工作台请求类")
public class WorkStationRequestBean extends PageRequestBean implements Serializable{
    private static final long serialVersionUID = 3083177023027501328L;

    @ApiModelProperty(value = "医生级别")
    private String level;

    @ApiModelProperty(value = "级别脱落总数")
    private Integer levelDropCount;

    @ApiModelProperty(value = "销售id，默认取登录的，前端传就用前端的")
    private Long drugUserId;

    @ApiModelProperty(value = "模糊查询销售，前端不用传")
    private String leaderPath;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "日期类型，1是今天，2是本周，3是本月，4是本季度")
    private Integer dateType;

    @ApiModelProperty(value = "最短总通话时长，前端不用传")
    private Integer minCallTotalTime;

    @ApiModelProperty(value = "最短平均总通话时长，根据人次，前端不用传")
    private Integer minAvgCallTotalTime;

    @ApiModelProperty(value = "最少的电话数量，前端不用传")
    private Integer minCallCount;

    @ApiModelProperty(value = "最少的电话覆盖医生数，前端不用传")
    private Integer minCallCoveredCount;

    @ApiModelProperty(value = "最少总短信数量，前端不用传")
    private Integer minImCount;

    @ApiModelProperty(value = "最少短信覆盖客户数，前端不用传")
    private Integer minImCoveredCount;

    @ApiModelProperty(value = "最少的微信数量，前端不用传")
    private Integer minWechatCount;

    @ApiModelProperty(value = "最少的微信覆盖，前端不用传")
    private Integer minWechatCoveredCount;

    @ApiModelProperty(value = "最少的邮件数量，前端不用传")
    private Integer minEmailCount;

    @ApiModelProperty(value = "最少的会议时长")
    private Integer minMeetingTime;


    @ApiModelProperty(value = "最少的邮件覆盖客户数，前端不用传")
    private Integer minEmailCoveredCount;

    @ApiModelProperty(value = "通话达标设置的值，前端不用传")
    private Integer callReach;

    @ApiModelProperty(value = "微信达标设置的值，前端不用传")
    private Integer wechatReach;

    @ApiModelProperty(value = "短信达标设置的值，前端不用传")
    private Integer imReach;

    @ApiModelProperty(value = "邮件达标设置的值，前端不用传")
    private Integer emailReach;



    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getLeaderPath() {
        return leaderPath;
    }

    public void setLeaderPath(String leaderPath) {
        this.leaderPath = leaderPath;
    }


    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }


    public Integer getCallReach() {
        return callReach;
    }

    public void setCallReach(Integer callReach) {
        this.callReach = callReach;
    }

    public Integer getWechatReach() {
        return wechatReach;
    }

    public void setWechatReach(Integer wechatReach) {
        this.wechatReach = wechatReach;
    }

    public Integer getImReach() {
        return imReach;
    }

    public void setImReach(Integer imReach) {
        this.imReach = imReach;
    }


    public Integer getMinCallTotalTime() {
        return minCallTotalTime;
    }

    public void setMinCallTotalTime(Integer minCallTotalTime) {
        this.minCallTotalTime = minCallTotalTime;
    }


    public Integer getMinAvgCallTotalTime() {
        return minAvgCallTotalTime;
    }

    public void setMinAvgCallTotalTime(Integer minAvgCallTotalTime) {
        this.minAvgCallTotalTime = minAvgCallTotalTime;
    }

    public Integer getMinCallCount() {
        return minCallCount;
    }

    public void setMinCallCount(Integer minCallCount) {
        this.minCallCount = minCallCount;
    }

    public Integer getMinCallCoveredCount() {
        return minCallCoveredCount;
    }

    public void setMinCallCoveredCount(Integer minCallCoveredCount) {
        this.minCallCoveredCount = minCallCoveredCount;
    }

    public Integer getMinImCount() {
        return minImCount;
    }

    public void setMinImCount(Integer minImCount) {
        this.minImCount = minImCount;
    }

    public Integer getMinImCoveredCount() {
        return minImCoveredCount;
    }

    public void setMinImCoveredCount(Integer minImCoveredCount) {
        this.minImCoveredCount = minImCoveredCount;
    }

    public Integer getMinWechatCount() {
        return minWechatCount;
    }

    public void setMinWechatCount(Integer minWechatCount) {
        this.minWechatCount = minWechatCount;
    }

    public Integer getMinWechatCoveredCount() {
        return minWechatCoveredCount;
    }

    public void setMinWechatCoveredCount(Integer minWechatCoveredCount) {
        this.minWechatCoveredCount = minWechatCoveredCount;
    }


    public Integer getMinEmailCount() {
        return minEmailCount;
    }

    public void setMinEmailCount(Integer minEmailCount) {
        this.minEmailCount = minEmailCount;
    }

    public Integer getMinEmailCoveredCount() {
        return minEmailCoveredCount;
    }

    public void setMinEmailCoveredCount(Integer minEmailCoveredCount) {
        this.minEmailCoveredCount = minEmailCoveredCount;
    }

    public Integer getEmailReach() {
        return emailReach;
    }

    public void setEmailReach(Integer emailReach) {
        this.emailReach = emailReach;
    }


    public Integer getMinMeetingTime() {
        return minMeetingTime;
    }

    public void setMinMeetingTime(Integer minMeetingTime) {
        this.minMeetingTime = minMeetingTime;
    }


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getLevelDropCount() {
        return levelDropCount;
    }

    public void setLevelDropCount(Integer levelDropCount) {
        this.levelDropCount = levelDropCount;
    }
}
