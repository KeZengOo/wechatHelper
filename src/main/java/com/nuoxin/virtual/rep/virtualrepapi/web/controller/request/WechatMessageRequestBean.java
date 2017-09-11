package com.nuoxin.virtual.rep.virtualrepapi.web.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "微信消息excel接收类")
public class WechatMessageRequestBean implements Serializable{
    private static final long serialVersionUID = 338219961625669631L;

    @ApiModelProperty(value = "微信消息id")
    private String id;

    @ApiModelProperty(value = "微信消息创建时间")
    private String wechatTime;

    @ApiModelProperty(value = "昵称或者备注")
    private String nickname;


    @ApiModelProperty(value = "微信号")
    private String wechatNumber;

    @ApiModelProperty(value = "微信消息状态")
    private String wechatMessageStatus;

    @ApiModelProperty(value = "微信消息类型")
    private String wechatMessageType;


    @ApiModelProperty(value = "微信消息")
    private String wechatMessage;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWechatTime() {
        return wechatTime;
    }

    public void setWechatTime(String wechatTime) {
        this.wechatTime = wechatTime;
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

    public String getWechatMessage() {
        return wechatMessage;
    }

    public void setWechatMessage(String wechatMessage) {
        this.wechatMessage = wechatMessage;
    }


    @Override
    public String toString() {
        return "WechatMessageRequestBean{" +
                "id='" + id + '\'' +
                ", wechatTime='" + wechatTime + '\'' +
                ", nickname='" + nickname + '\'' +
                ", wechatNumber='" + wechatNumber + '\'' +
                ", wechatMessageStatus='" + wechatMessageStatus + '\'' +
                ", wechatMessageType='" + wechatMessageType + '\'' +
                ", wechatMessage='" + wechatMessage + '\'' +
                '}';
    }
}
