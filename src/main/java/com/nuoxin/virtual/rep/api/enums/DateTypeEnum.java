package com.nuoxin.virtual.rep.api.enums;

/**
 * 日期类型
 * Create by tiancun on 2017/10/18
 */
public enum DateTypeEnum {

    DAY(1,"今天"),WEEK(2,"本周"),MONTH(3,"本月"),QUARTER(4,"本季度");

    private Integer value;

    private String name;

    DateTypeEnum(Integer value, String name){
        this.value =value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
