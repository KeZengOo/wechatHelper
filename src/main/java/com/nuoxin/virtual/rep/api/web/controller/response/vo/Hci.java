package com.nuoxin.virtual.rep.api.web.controller.response.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 主数据医院
 * Created by tiancun on 17/8/4.
 */
public class Hci implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private long id;

    private String adminAreaCode;

    private String name;

    private int medicalGrade;

    private String website;

    private String description;

    private Date createTime;

    private Date updateTime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAdminAreaCode() {
        return adminAreaCode;
    }

    public void setAdminAreaCode(String adminAreaCode) {
        this.adminAreaCode = adminAreaCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMedicalGrade() {
        return medicalGrade;
    }

    public void setMedicalGrade(int medicalGrade) {
        this.medicalGrade = medicalGrade;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
