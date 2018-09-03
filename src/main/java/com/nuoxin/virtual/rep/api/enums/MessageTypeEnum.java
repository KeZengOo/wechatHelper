package com.nuoxin.virtual.rep.api.enums;

/**
 * 消息类型
 * Create by tiancun on 2017/9/15
 */
public enum MessageTypeEnum {

    WECHAT(1,"微信"),
    IM(2,"短信"),
    EMAIL(3,"短信");

    private Integer messageType;

    private String messageTypeName;

    MessageTypeEnum(Integer messageType,String messageTypeName){
        this.messageType = messageType;
        this.messageTypeName = messageTypeName;
    }


    public Integer getMessageType() {
        return messageType;
    }

    public String getMessageTypeName() {
        return messageTypeName;
    }
}
