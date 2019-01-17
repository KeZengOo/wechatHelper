package com.nuoxin.virtual.rep.api.enums;

/**
 * 分享类型
 *
 * @author tiancun
 * @date 2018-09-20
 */
public enum ShareStatusEnum {

    SUCCESS(1, "成功拜访"),
    SERVICE(2, "服务"),
    NO_ANSWER(3, "无人回应");


    private Integer type;

    private String status;


    ShareStatusEnum(Integer type, String status) {
        this.type = type;
        this.status = status;
    }


    public Integer getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public static String getStatusByType(Integer t){
        if (SUCCESS.type.equals(t)){
            return SUCCESS.status;
        }else if (SERVICE.type.equals(t)){
            return SERVICE.status;
        }else if (NO_ANSWER.type.equals(t)){
            return NO_ANSWER.status;
        }
        return null;
    }
}
