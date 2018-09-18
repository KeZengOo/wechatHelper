package com.nuoxin.virtual.rep.api.web.controller.request.vo;

import com.nuoxin.virtual.rep.api.common.annotations.Excel;

import java.util.Date;

/**
 * 微信消息excel映射实体类
 * @author tiancun
 */
public class WechatMessageVo {

    @Excel(name = "ID", width = 50)
    private String id;

    @Excel(name = "时间", width = 30)
    private Date wechatTime;

    @Excel(name = "联系人", width = 30)
    private String nickname;

    @Excel(name = "微信号", width = 30)
    private String wechatNumber;

    @Excel(name = "状态", width = 8)
    private String messageStatus;

    @Excel(name = "类型", width = 20)
    private String messageType;

    @Excel(name = "消息", width = 1000)
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Date getWechatTime() {
        return wechatTime;
    }

    public void setWechatTime(Date wechatTime) {
        this.wechatTime = wechatTime;
    }
}

