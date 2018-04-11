package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 医生多个手机号
 * @author tiancun
 * @date 2018-04-11
 */
@Entity
@Table(name = "doctor_telephone")
public class DoctorTelephone extends IdEntity {


    private static final long serialVersionUID = -6122095806970708819L;


    /**
     * 医生手机号
     */
    @Column(name = "doctor_id")
    private Long doctorId;

    /**
     * 医生手机号
     */
    @Column(name = "telephone")
    private String telephone;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否可用，1是可用，0是不可用，默认是1
     */
    @Column(name = "is_available")
    private Integer isAvailable = 1;


    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }
}
