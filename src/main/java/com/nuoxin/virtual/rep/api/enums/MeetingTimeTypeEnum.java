package com.nuoxin.virtual.rep.api.enums;

/**
 * 导入微信消息的用户类型
 */
public enum MeetingTimeTypeEnum {

    DAY(1,"工作日"),
    HOUR(2,"小时");

    private int type;

    private String name;

    MeetingTimeTypeEnum(int type, String name){
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
