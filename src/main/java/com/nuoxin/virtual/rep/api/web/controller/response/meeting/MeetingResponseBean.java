package com.nuoxin.virtual.rep.api.web.controller.response.meeting;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/11
 */
@ApiModel(value = "返回的会议数据")
public class MeetingResponseBean implements Serializable{
    private static final long serialVersionUID = 6192894823069232237L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "讲者")
    private String speaker;

    @ApiModelProperty(value = "会议开始时间")
    private String meetingStartTime;

    @ApiModelProperty(value = "会议结束时间")
    private String meetingEndTime;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getMeetingStartTime() {
        return meetingStartTime;
    }

    public void setMeetingStartTime(String meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public String getMeetingEndTime() {
        return meetingEndTime;
    }

    public void setMeetingEndTime(String meetingEndTime) {
        this.meetingEndTime = meetingEndTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
