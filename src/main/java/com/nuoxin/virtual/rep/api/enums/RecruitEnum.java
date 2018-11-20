package com.nuoxin.virtual.rep.api.enums;

/**
 * 招募结果枚举
 * @author tiancun
 * @date 2018-11-19
 */
public enum RecruitEnum {

    UNKOWN(-1, "未知"),

    SUCCESS_RECRUIT(1, "成功招募"),

    DROP_OUT(2, "退出项目");


    /**
     * 类型
     */
    private Integer type;

    /**
     * 名称
     */
    private String name;

    RecruitEnum(Integer type, String name){
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
