package com.nuoxin.virtual.rep.api.enums;

/**
 * 角色类型
 * @author tiancun
 * @date 2018-11-05
 */
public enum RoleTypeEnum {

    MANAGER(102L, "管理员"),
    SALE(101L,"普通销售");


    private Long type;

    private String name;

    RoleTypeEnum(Long type, String name){
        this.type=type;
        this.name=name;
    }


    public Long getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
