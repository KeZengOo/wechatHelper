package com.nuoxin.virtual.rep.api.enums;

/**
 * 扩展的动态字段类型
 * @author tiancun
 * @date 2019-01-22
 */
public enum ExtendDynamicFieldTypeEnum {
    POTENTIAL(1,"潜力"),
    CLASSIFICATION(2,"分型");


    private int type;

    private String name;

    ExtendDynamicFieldTypeEnum(int type, String name){
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }


    public static String getNameByType(Integer t){
        if (CLASSIFICATION.type == t){
            return CLASSIFICATION.name;
        }else if (POTENTIAL.type == t){
            return POTENTIAL.name;
        }else {
            return "";
        }
    }

}
