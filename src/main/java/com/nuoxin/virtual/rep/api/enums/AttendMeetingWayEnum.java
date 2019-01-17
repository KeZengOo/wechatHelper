package com.nuoxin.virtual.rep.api.enums;

/**
 * 参会方式
 * @author tiancun
 * @date 2018-10-02
 */
public enum AttendMeetingWayEnum {

    /**
     * 网站
     */
    WEBSITE(1, "网站"),

    /**
     * 电话
     */
    TELEPHONE(2, "电话"),

    /**
     * 微信
     */
    WECHAT(3, "微信");

    private Integer type;

    private String name;

    AttendMeetingWayEnum(Integer type, String name){
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

        if (type.equals(WEBSITE.type)){
            return WEBSITE.name;
        }

        if(type.equals(TELEPHONE.type)){
            return TELEPHONE.name;
        }

        if (type.equals(WECHAT.type)){
            return WECHAT.name;
        }

        return "";
    }
}
