package com.nuoxin.virtual.rep.api.web.controller.response.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/9/18
 */
@ApiModel(value = "消息联系人")
public class MessageLinkmanResponseBean implements Serializable{
    private static final long serialVersionUID = -5394684010482299093L;

    @ApiModelProperty(value = "医生id")
    private Long doctorId;

    @ApiModelProperty(value = "昵称或者备注")
    private String nickname;

    @ApiModelProperty(value = "消息类型，1是微信，2是短信")
    private Integer messageType;

    @ApiModelProperty(value = "最后一次聊天时间")
    private String lastTime;

    @ApiModelProperty(value = "最后一次聊天信息")
    private String lastMessage;


    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }


    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }
}
