package com.nuoxin.virtual.rep.api.enums;

/**
 * 角色类型
 * @author tiancun
 * @date 2018-11-05
 */
public enum RoleTypeEnum {

    MANAGER(102L, "管理员"),
    SALE(101L,"普通销售"),
    PROJECT_MANAGER(103L, "项目管理员"),
    RECRUIT_SALE(104L, "招募代表"),
    COVER_SALE(105L, "覆盖代表");



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
