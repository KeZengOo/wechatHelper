package com.nuoxin.virtual.rep.api.enums;

/**
 * 拜访类型枚举
 * @author tiancun
 * @date 2019-01-07
 */
public enum VisitTypeEnum {

    VISIT(1, "拜访"),
    RET_VISIT(2, "转以后拜访"),
    BEFORE_MEETING(3, "会议开始前"),
    AFTER_MEETING(4, "会议结束后");



    private Integer type;

    private String name;

    VisitTypeEnum(Integer type, String name){
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
