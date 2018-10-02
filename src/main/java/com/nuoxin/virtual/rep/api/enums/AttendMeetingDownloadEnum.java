package com.nuoxin.virtual.rep.api.enums;

/**
 * 参会是否下载
 * @author tiancun
 * @date 2018-10-02
 */
public enum AttendMeetingDownloadEnum {

    /**
     * 下载
     */
    DOWNLOAD(1, "下载"),

    /**
     * 未下载
     */
    NO_DOWNLOAD(2, "未下载");


    private Integer type;

    private String name;

    AttendMeetingDownloadEnum(Integer type, String name){
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

        if (type.equals(DOWNLOAD.type)){
            return DOWNLOAD.name;
        }

        if(type.equals(NO_DOWNLOAD.type)){
            return NO_DOWNLOAD.name;
        }

        return "";
    }
}
