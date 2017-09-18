package com.nuoxin.virtual.rep.api.web.controller.response.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/9/18
 */
@ApiModel("今日会话统计")
public class MessageCountResponseBean implements Serializable{
    private static final long serialVersionUID = 2114059639134401699L;

    @ApiModelProperty(value = "消息类型，1是微信2是短信")
    private Integer messageType;

    @ApiModelProperty(value = "会话数")
    private Integer messageCount;


    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }
}
