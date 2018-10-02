package com.nuoxin.virtual.rep.api.enums;

/**
 * 参会类型
 * @author tiancun
 * @date 2018-10-02
 */
public enum AttendMeetingTypeEnum {

    /**
     * 参会
     */
    ATTEND(1, "参会"),

    /**
     * 查看
     */
    VIEW(2, "查看");

    private Integer type;

    private String name;

    AttendMeetingTypeEnum(Integer type, String name){
        this.type = type;
        this.name = name;
    }


    public Integer getType() {
        return type;
    }


    public String getName() {
        return name;
    }



    public static String getNameByType(Integer type){
        if (type == null){
            return "";
        }

        if (type.equals(ATTEND.type)){
            return ATTEND.name;
        }

        if(type.equals(VIEW.type)){
            return VIEW.name;
        }
        return "";
    }
}
