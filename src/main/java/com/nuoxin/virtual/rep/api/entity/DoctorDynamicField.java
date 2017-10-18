package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Create by tiancun on 2017/9/22
 */
@Entity
@Table(name = "virtual_doctor_dynamic_field")
public class DoctorDynamicField extends IdEntity{

    //字段名称
    @Column(name = "field_name")
    private String name;

    //字段别名
    @Column(name = "alias")
    private String alias;

    //字段类型，1文本，2下拉框
    @Column(name = "type")
    private Integer type;

    //字段类型的值
    @Column(name = "field_value")
    private String value;

    //分类，目前1基本信息，2医生的处方信息，3之前拜访记录，4分析
    @Column(name = "classification")
    private Integer classification;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getClassification() {
        return classification;
    }

    public void setClassification(Integer classification) {
        this.classification = classification;
    }
}
