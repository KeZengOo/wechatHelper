package com.nuoxin.virtual.rep.api.enums;

/**
 * 拜访频次，时间类型
 * @author tiancun
 * @date 2019-01-10
 */
public enum VisitFrequencyTypeEnum {

    DAY(1,"小时"),
    SEARCH_TWO(2,"工作日");

    private int userType;

    private String userTypeName;

    VisitFrequencyTypeEnum(int userType, String userTypeName){
        this.userType = userType;
        this.userTypeName = userTypeName;
    }

    public int getUserType() {
        return userType;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

}
