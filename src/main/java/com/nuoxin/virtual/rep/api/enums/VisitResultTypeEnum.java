package com.nuoxin.virtual.rep.api.enums;

/**
 * 拜访结果类型枚举
 * @author tiancun
 * @date 2018-11-20
 */
public enum VisitResultTypeEnum {

    CONTACT(1, "接触医生"),
    SUCCESS(2, "成功医生"),
    COVER(3,"覆盖医生");


    private Integer type;


    private String name;


    VisitResultTypeEnum(Integer type, String name){
        this.type = type;
        this.name = name;
    }


    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
