package com.nuoxin.virtual.rep.api.common.enums;

/**
 * 医生动态字段分类
 * @author tiancun
 * @date 2018-09-17
 */
public enum ClassificationEnum {

    BASIC(1,"基本信息"),
    PRESCRIPTION(2,"处方"),
    VISIT_RECORD(3, "拜访记录"),
    ANALYSIS(4, "分析"),
    HOSPITAL(5, "医院");


    private Integer type;


    private String name;


    ClassificationEnum(Integer type, String name){
        this.type=type;
        this.name=name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
