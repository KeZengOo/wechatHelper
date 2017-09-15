package com.nuoxin.virtual.rep.api.web.controller.response.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/9/14
 */
@ApiModel(value = "微信消息或者短信消息")
public class MessageResponseBean implements Serializable{
    private static final long serialVersionUID = -3477145970782301979L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "销售代表或者医生id")
    private Long userId;

    @ApiModelProperty(value = "用户类型，1是销售代表，2是医生")
    private Integer userType;

    @ApiModelProperty(value = "联系人昵称或者备注")
    private String nickname;

    @ApiModelProperty(value = "微信号")
    private String wechatNumber;

    @ApiModelProperty(value = "手机号")
    private String telephone;

    @ApiModelProperty(value = "微信消息的状态，发送或者接收")
    private String wechatMessageStatus;

    @ApiModelProperty(value = "微信消息的类型")
    private String wechatMessageType;

    @ApiModelProperty(value = "微信聊天消息或者短信消息")
    private String message;

    @ApiModelProperty(value = "消息类型，1是微信，2是短信")
    private Integer messageType;

    @ApiModelProperty(value = "微信聊天时间或者短信发送时间")
    private String messageTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getWechatMessageStatus() {
        return wechatMessageStatus;
    }

    public void setWechatMessageStatus(String wechatMessageStatus) {
        this.wechatMessageStatus = wechatMessageStatus;
    }

    public String getWechatMessageType() {
        return wechatMessageType;
    }

    public void setWechatMessageType(String wechatMessageType) {
        this.wechatMessageType = wechatMessageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
