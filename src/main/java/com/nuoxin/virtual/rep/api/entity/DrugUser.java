package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by fenggang on 9/11/17.
 */
@Entity
@Table(name = "drug_user")
public class DrugUser extends IdEntity {

    private static final long serialVersionUID = 9090513690988883094L;

    @Column(name = "name")
    private String name;
    @Transient
    private Long eappId;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "password")
    private String password;
    @Column(name = "new_password")
    private String newPassword;
    @Column(name = "email")
    private String email;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "drug_name")
    private String drugName;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "call_info")
    private String callInfo;
    @Column(name = "leader_id")
    private Long leaderId;
    @Column(name = "leader_path")
    private String leaderPath;
    @Column(name = "user_type")
    private Integer userType;

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

    public String getCallInfo() {
        return callInfo;
    }

    public void setCallInfo(String callInfo) {
        this.callInfo = callInfo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public String getLeaderPath() {
        return leaderPath;
    }

    public void setLeaderPath(String leaderPath) {
        this.leaderPath = leaderPath;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}
