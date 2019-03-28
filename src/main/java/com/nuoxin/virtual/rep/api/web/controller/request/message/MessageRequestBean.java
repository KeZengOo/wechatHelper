package com.nuoxin.virtual.rep.api.web.controller.request.message;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "微信消息请求参数")
public class MessageRequestBean extends PageRequestBean implements Serializable{
    private static final long serialVersionUID = 338219961625669631L;

    @ApiModelProperty(value = "微信消息id")
    private Long id;

    @ApiModelProperty(value = "医生id")
    private Long doctorId;

    @ApiModelProperty(value = "用户类型,1是销售代表，2是医生")
    private Integer userType;

    @ApiModelProperty(value = "用户名称或者备注")
    private String nickname;


    @ApiModelProperty(value = "微信号")
    private String wechatNumber;

    @ApiModelProperty(value = "手机号")
    private String telephone;

    @ApiModelProperty(value = "消息类型,1是微信,2是短信")
    private Integer messageType;

    @ApiModelProperty(value = "微信聊天时间")
    private String wechatTime;

    @ApiModelProperty(value = "销售代表id")
    private Long drugUserId;

    @ApiModelProperty(value = "聊天开始时间")
    private String startTime;


    @ApiModelProperty(value = "聊天结束时间")
    private String endTime;


    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "前端不用传")
    private String leaderPath;


    @ApiModelProperty(value = "时间筛选，0或者null为全部，1是近一个月，2是近三个")
    private Integer timeType;

    @ApiModelProperty(value = "选择的互动代表ID")
    private List<Long> drugUserIdList;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getWechatNumber() {
        return wechatNumber;
    }

    public void setWechatNumber(String wechatNumber) {
        this.wechatNumber = wechatNumber;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWechatTime() {
        return wechatTime;
    }

    public void setWechatTime(String wechatTime) {
        this.wechatTime = wechatTime;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }


    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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


    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public List<Long> getDrugUserIdList() {
        return drugUserIdList;
    }

    public void setDrugUserIdList(List<Long> drugUserIdList) {
        this.drugUserIdList = drugUserIdList;
    }
}
