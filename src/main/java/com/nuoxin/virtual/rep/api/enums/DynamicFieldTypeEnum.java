package com.nuoxin.virtual.rep.api.enums;

/**
 * 动态字段type说明
 * @author tiancun
 * @date 2019-01-29
 */
public enum DynamicFieldTypeEnum {

    // 字段类型，1是输入框，2单选框，3单选下拉，4联系方式，5多选下拉，6数字

   INPUT(1, "输入框"),
    RADIO(2, "单选框"),
    SINGLE(3, "下拉单选"),
    CONTACT(4, "联系方式"),
    CHECKBOX(5, "多选下拉"),
    NUMBER(6, "数字");

   private Integer type;

   private String name;

   DynamicFieldTypeEnum(Integer type, String name){
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
