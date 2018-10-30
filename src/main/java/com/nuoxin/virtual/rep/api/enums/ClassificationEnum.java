package com.nuoxin.virtual.rep.api.enums;

/**
 * 动态字段类型
 * @author tiancun
 * @date 2018-10-30
 */
public enum ClassificationEnum {

    BASIC(1, "基本信息"),
    HOSPITAL(5, "医院信息"),

    PRESCRIPTION(2, "处方信息"),
    VISIT(3, "拜访记录"),

    ANALYSIS(4, "分析");


    private Integer type;

    private String name;

    ClassificationEnum(Integer type, String name){
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
