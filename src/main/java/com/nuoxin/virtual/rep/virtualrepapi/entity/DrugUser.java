package com.nuoxin.virtual.rep.virtualrepapi.entity;

import com.nuoxin.virtual.rep.virtualrepapi.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fenggang on 9/11/17.
 */
@Entity
@Table(name = "virtual_drug_user")
public class DrugUser extends IdEntity {

    private static final long serialVersionUID = 9090513690988883094L;

    @Column(name = "name")
    private String name;
    @Column(name = "eapp_id")
    private Long eappId;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "drug_name")
    private String drugName;
    @Column(name = "create_time")
    private Date createTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEappId() {
        return eappId;
    }

    public void setEappId(Long eappId) {
        this.eappId = eappId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
