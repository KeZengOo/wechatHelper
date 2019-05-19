package com.nuoxin.virtual.rep.api.enums;

/**
 * @author tiancun
 * @date 2018-12-27
 */
public enum  OnOffLineEnum {

    ONLINE(1,"线上"),
    OFFLINE(2,"线下"),
    OTHER(0, "经理");


    private Integer userType;

    private String userTypeName;

    OnOffLineEnum(Integer userType,String userTypeName){
        this.userType = userType;
        this.userTypeName = userTypeName;
    }

    public Integer getUserType() {
        return userType;
    }

    public String getUserTypeName() {
        return userTypeName;
    }
}
