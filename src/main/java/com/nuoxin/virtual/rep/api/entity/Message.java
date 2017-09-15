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
@Table(name = "virtual_message")
public class Message extends IdEntity {

    private static final long serialVersionUID = -7724291148759601691L;

    //销售代表或者医生id
    @Column(name = "user_id")
    private Long userId;

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

    //微信消息的状态，发送或者接收
    @Column(name = "wechat_message_status")
    private String wechatMessageStatus;

    //微信消息
    @Column(name = "message")
    private String message;

    //微信消息类型
    @Column(name = "wechat_message_type")
    private String wechatMessageType;

    //消息类型,1是微信 2是短信
    @Column(name = "message_type")
    private Integer messageType;

    //微信聊天时间或者短信发送时间
    @Column(name = "message_time")
    private String messageTime;

    //消息录入时间
    @Column(name = "create_time")
    private Date createTime;


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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWechatMessageType() {
        return wechatMessageType;
    }

    public void setWechatMessageType(String wechatMessageType) {
        this.wechatMessageType = wechatMessageType;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
