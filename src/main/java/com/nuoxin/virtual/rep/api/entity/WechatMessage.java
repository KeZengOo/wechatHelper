package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 微信消息类
 */
@Entity
@Table(name = "virtual_wechat_message")
public class WechatMessage extends IdEntity {

    // 用户类型，1是销售代表，2是医生
    @Column(name = "user_type")
    private Integer userType;

    //昵称或者备注
    @Column(name = "nickname")
    private String nickname;

    //微信号
    @Column(name = "wechat_number")
    private String wechatNumber;

    //手机号
    @Column(name = "telephone")
    private String telephone;

    //消息的状态，发送或者接收
    @Column(name = "message_status")
    private String messageStatus;

    //微信消息
    @Column(name = "message")
    private String message;

    //消息类型
    @Column(name = "message_type")
    private String messageType;

    //微信聊天时间
    @Column(name = "wechat_time")
    private String wechatTime;


    //消息录入时间
    @Column(name = "create_time")
    private Date createTime;


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

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getWechatTime() {
        return wechatTime;
    }

    public void setWechatTime(String wechatTime) {
        this.wechatTime = wechatTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
