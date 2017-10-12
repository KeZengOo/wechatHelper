package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by fenggang on 9/14/17.
 */
@Entity
@Table(name = "virtual_drug_user_doctor")
public class DrugUserDoctor extends IdEntity {

    private static final long serialVersionUID = 4284825738968331250L;

    @Column(name = "virtual_doctor_id")
    private Long doctorId;
    @Column(name = "virtual_drug_user_id")
    private Long drugUserId;
    @Column(name = "product_id")
    private Long productId;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
