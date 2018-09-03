package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fenggang on 10/13/17.
 */
@Entity
@Table(name = "virtual_email_info")
public class Email extends IdEntity {
    private static final long serialVersionUID = 5712743512857606299L;

    @Column(name = "doctor_id")
    private Long doctorId;
    @Column(name = "drug_user_id")
    private Long drugUserId;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "type")
    private Integer type;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "create_time")
    private Date createTime;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
