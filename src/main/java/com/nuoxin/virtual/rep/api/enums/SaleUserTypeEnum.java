package com.nuoxin.virtual.rep.api.enums;

/**
 * 导入微信消息的用户类型
 */
public enum SaleUserTypeEnum {

    DRUG_USER(1,"销售代表"),
    SALE_MANAGER(2,"销售经理"),
    PRODUCT_MANAGER(3, "产品经理");


    private Integer userType;

    private String userTypeName;

    SaleUserTypeEnum(Integer userType, String userTypeName){
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
