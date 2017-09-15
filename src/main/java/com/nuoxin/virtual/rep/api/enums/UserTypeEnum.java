package com.nuoxin.virtual.rep.api.enums;

/**
 * 导入微信消息的用户类型
 */
public enum UserTypeEnum {

    DRUG_USER(1,"销售代表"),
    DOCTOR(2,"医生");

    private int userType;

    private String userTypeName;

    UserTypeEnum(int userType, String userTypeName){
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
